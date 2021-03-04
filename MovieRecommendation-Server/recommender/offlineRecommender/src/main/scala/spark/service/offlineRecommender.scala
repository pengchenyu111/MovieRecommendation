package spark.service

import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.mllib.recommendation.{ALS, MatrixFactorizationModel, Rating}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.jblas.DoubleMatrix
import spark.constant.DBConstant
import spark.entity.{BaseRecommendation, MovieRecs, MovieUserRatings, UserRecs}
import spark.service.ALSTrainer.getClass
import spark.util.MongoDBUtil

/**
 * 离线推荐服务算法
 *
 * @author PengChenyu
 * @since 2021-02-22 17:42:24
 */
object offlineRecommender {

  // 日志
  private val logger: Logger = Logger.getLogger(getClass)

  def main(args: Array[String]): Unit = {
    // 环境配置
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("OfflineRecommeder")
    val spark: SparkSession = SparkSession.builder()
      .config(sparkConf)
      .config("spark.mongodb.input.uri", DBConstant.MONGO_URL)
      .config("spark.mongodb.output.uri", DBConstant.MONGO_URL)
      //.config("spark.local.dir", "D:\\usr\\sparkTmpData")
      .getOrCreate()
    import spark.implicits._

    // 加载数据库中的评分数据
    val ratingRDD: RDD[(Int, Int, Double)] = spark.read
      .format("jdbc")
      .option("url", DBConstant.MYSQL_URL)
      .option("driver", DBConstant.MYSQL_DRIVER)
      .option("user", DBConstant.MYSQL_USER)
      .option("password", DBConstant.MYSQL_PWD)
      .option("dbtable", DBConstant.MYSQL_TABLE_MOVIE_USER_RATINGS)
      .load()
      .as[MovieUserRatings]
      .rdd
      .map(rating => (rating.user_id, rating.douban_id, rating.user_movie_rating))
      .cache()


    // 转换为Rating格式
    val trainRDD: RDD[Rating] = ratingRDD.map(x => Rating(x._1, x._2, x._3))
    // 设置参数(由ALSTrainer训练得出最优参数)，训练模型
    val (rank, iterations, lambda) = (50, 3, 0.34)
    val model: MatrixFactorizationModel = ALS.train(trainRDD, rank, iterations, lambda)


    // 调用框架的recommendProductsForUsers方法，为所有用户推荐20条电影
    val userRecsDF: DataFrame = model.recommendProductsForUsers(20).map {
      case (user_id, arr) =>
        UserRecs(user_id, arr.sortWith(_.rating > _.rating).map(x => BaseRecommendation(x.product, x.rating)))
    }.toDF()
    // 存入MongoDB
    MongoDBUtil.storeDFInMongoDB(userRecsDF, DBConstant.MONGO_COLLECTION_ALS_USER_RECS)

    //    // 提取所有user_id 和 douban_id
    //    val userRDD: RDD[Int] = ratingRDD.map(_._1).distinct()
    //    val movieRDD: RDD[Int] = ratingRDD.map(_._2).distinct()
    //    // 计算user和movie的笛卡尔积，得到一个空评分矩阵
    //    val userMovies: RDD[(Int, Int)] = userRDD.cartesian(movieRDD)
    //    // 使用模型对空矩阵预测评分
    //    val predictRatings: RDD[Rating] = model.predict(userMovies)
    //
    //    // 选取合乎要求的项，得到用户推荐列表
    //    val userRecsDF: DataFrame = predictRatings
    //      .filter(_.rating > 0)
    //      .map(x => (x.user, (x.product, x.rating)))
    //      .groupByKey()
    //      .map {
    //        case (user, recs) =>
    //          UserRecs(user, recs.toList.sortWith(_._2 > _._2).take(20).map(x => BaseRecommendation(x._1, x._2)))
    //      }.toDF()
    //
    //    // 存入MongoDB
    //    MongoDBUtil.storeDFInMongoDB(userRecsDF, DBConstant.MONGO_COLLECTION_ALS_USER_RECS)


    // 基于电影隐特征，计算电影的相似度列表
    val movieFeatures: RDD[(Int, DoubleMatrix)] = model.productFeatures.map {
      case (mid, features) => (mid, new DoubleMatrix(features))
    }
    // 用电影特征列表和自己做笛卡尔积，计算两两电影的相似度
    val movieSimRecsDF: DataFrame = movieFeatures.cartesian(movieFeatures)
      .filter {
        case (a, b) => a._1 != b._1
      }
      .map {
        case (a, b) => {
          val sim: Double = cosSim(a._2, b._2)
          (a._1, (b._1, sim))
        }
      }
      .filter(_._2._2 > 0.7)
      .groupByKey()
      .map {
        case (mid, recs) => MovieRecs(mid, recs.toList.sortWith(_._2 > _._2).take(500).map(x => BaseRecommendation(x._1, x._2)))
      }
      .toDF()


    // 存入MongoDB
    MongoDBUtil.storeDFInMongoDB(movieSimRecsDF, DBConstant.MONGO_COLLECTION_ALS_MOVIE_SIM)


    // 关闭环境
    spark.close()
  }

  // 求向量余弦相似度
  def cosSim(movie1: DoubleMatrix, movie2: DoubleMatrix): Double = {
    movie1.dot(movie2) / (movie1.norm2() * movie2.norm2())
  }

}
