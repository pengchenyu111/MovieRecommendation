package com.pcy.movierecommendation.spark.service

import com.pcy.movierecommendation.spark.constant.{DBConstant, KafkaConstant}
import com.pcy.movierecommendation.spark.entity.{BaseRecommendation, MovieRecs, UserRecs}
import com.pcy.movierecommendation.spark.util.ConnectHelper
import com.pcy.movierecommendation.spark.util.Helpers._
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord}
import org.apache.log4j.Logger
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.CodecRegistry
import org.jblas.DoubleMatrix
import org.mongodb.scala.MongoClient.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.result.{DeleteResult, InsertOneResult}
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase}
import redis.clients.jedis.Jedis

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
 *
 * 实时推荐服务
 *
 * @author PengChenyu
 * @since 2021-02-28 22:08:22
 */
object StreamingRecommender {

  // 日志
  private val logger: Logger = Logger.getLogger(getClass)

  val MAX_USER_RATINGS_NUM: Int = 20
  val MAX_SIM_MOVIES_NUM: Int = 20
  val MAX_REC_NUM: Int = 20

  def main(args: Array[String]): Unit = {
    // 环境配置
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("StreamingRecommeder")
    val spark: SparkSession = SparkSession.builder()
      .config(sparkConf)
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext
    val ssc = new StreamingContext(sc, Seconds(3))

    // 加载电影相似度矩阵
    val codecRegistry: CodecRegistry = fromRegistries(fromProviders(classOf[MovieRecs], classOf[BaseRecommendation]), DEFAULT_CODEC_REGISTRY)
    val database: MongoDatabase = MongoClient(DBConstant.MONGO_SCALA_DRIVER_CONNECT_URL)
      .getDatabase(DBConstant.MONGO_DB)
      .withCodecRegistry(codecRegistry)
    val alsMovieSim: MongoCollection[MovieRecs] = database.getCollection(DBConstant.MONGO_COLLECTION_ALS_MOVIE_SIM)
    val simMovieMatrix: Map[Int, Map[Int, Double]] = alsMovieSim.find().results()
      .map {
        movieRecs => (movieRecs.douban_id, movieRecs.recs.map(x => (x.id, x.score)).toMap)
      }
      .toMap
    // 将其广播出去
    val simMovieMatrixBroadCast: Broadcast[collection.Map[Int, Map[Int, Double]]] = sc.broadcast(simMovieMatrix)

    // Kafka配置
    val kafkaParam: Map[String, Object] = Map[String, Object](
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> KafkaConstant.BOOTSTRAP_SERVERS_CONFIG,
      ConsumerConfig.GROUP_ID_CONFIG -> KafkaConstant.GROUP_ID,
      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG -> KafkaConstant.SERIALIZER,
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG -> KafkaConstant.SERIALIZER,
      ConsumerConfig.AUTO_OFFSET_RESET_CONFIG -> KafkaConstant.AUTO_OFFSET_RESET
    )
    // 通过kafka创建一个DStream
    val kafkaStream: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream[String, String](
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](Set(KafkaConstant.TOPIC_RATING), kafkaParam)
    )

    // 接受来自kafka的数据
    // 数据格式：userId|doubanId|score|timestamp
    val ratingStream: DStream[(Int, Int, Double, String)] = kafkaStream.map {
      msg =>
        val attr: Array[String] = msg.value().split("\\|")
        (attr(0).toInt, attr(1).toInt, attr(2).toDouble, attr(3))
    }

    // 开始做流式处理
    ratingStream.foreachRDD {
      rdds =>
        rdds.foreach {
          case (user_id, douban_id, score, timestamp) => {
            logger.info(">>>>>>>>>>>>>>>> 评分数据来临>>>>>>>>>>>>>>>>" + (user_id, douban_id, score, timestamp))
            // 1. 从redis里获取当前用户最近的K次评分，保存成Array[(mid, score)]
            val userRecentlyRatings: Array[(Int, Double)] = getUserRecentlyRating(MAX_USER_RATINGS_NUM, user_id, ConnectHelper.jedis)
            logger.info("用户%d最近k次评分===>%s".format(user_id, userRecentlyRatings.mkString(",")))

            // 2. 从相似度矩阵中取出当前电影最相似的N个电影，作为备选列表，Array[mid]
            val candidateMovies: Array[Int] = getTopSimMovies(MAX_SIM_MOVIES_NUM, douban_id, user_id, simMovieMatrixBroadCast.value)
            logger.info("备选电影列表===>%s".format(candidateMovies.mkString(",")))

            // 3. 对每个备选电影，计算推荐优先级，得到当前用户的实时推荐列表，Array[(mid, score)]
            val streamRecs: Array[(Int, Double)] = computeMovieScores(candidateMovies, userRecentlyRatings, simMovieMatrixBroadCast.value)
            logger.info("用户%d最终实时推荐列表===>%s".format(user_id, streamRecs.mkString(",")))

            // 4. 把推荐数据保存到mongodb
            saveDataToMongoDB(user_id, streamRecs)
          }
        }
    }

    // 启动
    ssc.start()

    // 等待处理关闭
    ssc.awaitTermination()
  }

  // redis操作返回的是java类，为了用map操作需要引入转换类

  import scala.collection.JavaConverters._

  /**
   * 获取当前用户最近的K次评分
   *
   * @param num     获取数量
   * @param user_id 用户id
   * @param jedis   连接对象
   * @return 最近K次评分列表
   */
  def getUserRecentlyRating(num: Int, user_id: Int, jedis: Jedis): Array[(Int, Double)] = {
    // List类型，key实例：rec:rating:userId:4  value为：douban_id:score
    val key: String = "rec:rating:userId:" + user_id
    jedis.lrange(key, 0, num - 1)
      .asScala
      .map {
        item =>
          val data: Array[String] = item.split(":")
          (data(0).trim.toInt, data(1).trim.toDouble)
      }
      .toArray
  }

  /**
   * 获取跟当前电影做相似的num个电影，作为备选电影
   *
   * @param num       相似电影的数量
   * @param douban_id 当前电影ID
   * @param user_id   当前评分用户ID
   * @param simMovies 相似度矩阵
   * @return 过滤之后的备选电影列表
   */
  def getTopSimMovies(num: Int,
                      douban_id: Int,
                      user_id: Int,
                      simMovies: scala.collection.Map[Int, scala.collection.immutable.Map[Int, Double]]): Array[Int] = {
    // 从相似度矩阵中拿到所有相似的电影
    val allSimMovies: Array[(Int, Double)] = simMovies(douban_id).toArray

    // 从Redis中查询用户已看过的电影
    // 这里的0传入后num-1为-1，表示该列表的所有数据
    val ratingExist: Array[Int] = getUserRecentlyRating(0, user_id, ConnectHelper.jedis).map(x => x._1).distinct

    // 把看过的过滤得到备选列表
    allSimMovies
      .filter(x => !ratingExist.contains(x._1))
      .sortWith(_._2 > _._2)
      //.take(num)
      .map(x => x._1)
  }


  /**
   * 计算电影优先级
   *
   * @param candidateMovies     备选电影列表
   * @param userRecentlyRatings 最近K次评分列表
   * @param simMovies           电影相似度列表
   * @return 电影优先级列表
   */
  def computeMovieScores(candidateMovies: Array[Int],
                         userRecentlyRatings: Array[(Int, Double)],
                         simMovies: scala.collection.Map[Int, scala.collection.immutable.Map[Int, Double]]): Array[(Int, Double)] = {

    // 用于保存每一个备选电影的基础得分
    val scores: ArrayBuffer[(Int, Double)] = ArrayBuffer[(Int, Double)]()
    // 保存每一个备选电影的增强减弱因子
    val increaseMap: mutable.HashMap[Int, Int] = mutable.HashMap[Int, Int]()
    val decreaseMap: mutable.HashMap[Int, Int] = mutable.HashMap[Int, Int]()

    for (candidateMovie <- candidateMovies; userRecentlyRating <- userRecentlyRatings) {
      // 拿到备选电影和最近评分电影的相似度
      val simScore: Double = getMoviesSimScore(candidateMovie, userRecentlyRating._1, simMovies)
      // 首先对于每个候选电影q，从用户u 最近的K 个评分中，找出与q 相似度较高（>=0.7）的u 已评分电影们，
      // 对于这些电影们中的每个电影r，将r 与q 的相似度乘以用户u 对r 的评分，将这些乘积计算平均数，作为用户u 对商品q 的评分预测
      if (simScore > 0.7) {
        // 计算各备选电影的基础推荐得分总分
        scores += ((candidateMovie, simScore * userRecentlyRating._2))
        // 记录本身评分较高（>3）的电影个数
        if (userRecentlyRating._2 > 3) {
          increaseMap(candidateMovie) = increaseMap.getOrElse(candidateMovie, 0) + 1
        } else {
          decreaseMap(candidateMovie) = decreaseMap.getOrElse(candidateMovie, 0) + 1
        }
      }
    }
    // 根据备选电影的id做groupBy，根据公式去求最后的推荐评分
    scores
      .groupBy(_._1)
      .map {
        case (mid, scoreList) =>
          (mid, scoreList.map(_._2).sum / scoreList.length + log(increaseMap.getOrElse(mid, 1)) - log(decreaseMap.getOrElse(mid, 1)))
      }
      .toArray
      .sortWith(_._2 > _._2)
      .take(MAX_REC_NUM)
  }

  /**
   * 求向量余弦相似度
   *
   * @param movie1 电影矩阵1
   * @param movie2 电影矩阵2
   * @return
   */
  def cosSim(movie1: DoubleMatrix, movie2: DoubleMatrix): Double = {
    movie1.dot(movie2) / (movie1.norm2() * movie2.norm2())
  }


  /**
   * 从电影相似度列表中获取两个电影之间的相似度
   *
   * @param mid1      电影1的id
   * @param mid2      电影2的id
   * @param simMovies 电影相似度列表
   * @return
   */
  def getMoviesSimScore(mid1: Int, mid2: Int, simMovies: scala.collection.Map[Int, scala.collection.immutable.Map[Int, Double]]): Double = {
    simMovies.get(mid1) match {
      case Some(sims) => sims.get(mid2) match {
        case Some(score) => score
        case None => 0.0
      }
      case None => 0.0
    }
  }


  /**
   * 求一个数的对数，利用换底公式，底数默认为10
   *
   * @param m 输入数
   * @return
   */
  def log(m: Int): Double = {
    val N = 10
    math.log(m) / math.log(N)
  }

  /**
   * 将数据保存到MongoDB
   * 选用mongo-scala-driver
   * 官方文档：http://mongodb.github.io/mongo-java-driver/4.2/driver-scala/getting-started/quick-start-case-class/
   *
   * @param user_id    用户id
   * @param streamRecs 最终推荐列表
   */
  def saveDataToMongoDB(user_id: Int, streamRecs: Array[(Int, Double)]): Unit = {
    // 获取连接
    val codecRegistry: CodecRegistry = fromRegistries(fromProviders(classOf[UserRecs], classOf[BaseRecommendation]), DEFAULT_CODEC_REGISTRY)
    val database: MongoDatabase = MongoClient(DBConstant.MONGO_SCALA_DRIVER_CONNECT_URL)
      .getDatabase(DBConstant.MONGO_DB)
      .withCodecRegistry(codecRegistry)
    val streamingRatingUserRecsCol: MongoCollection[UserRecs] = database.getCollection(DBConstant.MONGO_COLLECTION_STREAMING_RATING_USER_RECS)

    // 组装对象
    val userRecs = UserRecs(user_id, streamRecs.map(x => BaseRecommendation(x._1, x._2)).toSeq)

    // 插入集合
    val deleteInfo: DeleteResult = streamingRatingUserRecsCol.deleteOne(equal("user_id", user_id)).headResult()
    val insertInfo: InsertOneResult = streamingRatingUserRecsCol.insertOne(userRecs).headResult()
    logger.info("用户%d删除旧记录行数===>%d ,插入新纪录===>%s".format(user_id, deleteInfo.getDeletedCount, insertInfo.toString))

  }


}
