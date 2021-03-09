package com.pcy.movierecommendation.spark.service

import com.pcy.movierecommendation.spark.constant.DBConstant
import com.pcy.movierecommendation.spark.entity.{BaseRecommendation, MovieRecs, UserRecs}
import com.pcy.movierecommendation.spark.util.Helpers._
import org.apache.log4j.Logger
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.CodecRegistry
import org.mongodb.scala.MongoClient.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase}

/**
 *
 * 实时推荐服务
 *
 * @author PengChenyu
 * @since 2021-02-28 22:08:22
 */
object TestRecommender {

  // 日志
  private val logger: Logger = Logger.getLogger(getClass)

  val MAX_USER_RATINGS_NUM: Int = 20

  def main(args: Array[String]): Unit = {
    //    // 环境配置
    //    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("StreamingRecommeder")
    //    val spark: SparkSession = SparkSession.builder()
    //      .config(sparkConf)
    //      .config("spark.mongodb.input.uri", DBConstant.MONGO_URL)
    //      .config("spark.mongodb.output.uri", DBConstant.MONGO_URL)
    //      .getOrCreate()
    //    val sc: SparkContext = spark.sparkContext
    //    sc.setLogLevel("WARN")
    //    val ssc = new StreamingContext(sc, Seconds(3))
    //
    //
    //    //    val frame: DataFrame = MongoSpark.load(spark)
    //    //    frame.createTempView("als_movie_sim")
    //    //
    //    //    spark.sql("select * from als_movie_sim where user_id = 119058").show()
    //
    //    //    val cnt: Long = spark.read
    //    //      .option("uri", DBConstant.MONGO_URL)
    //    //      .option("collection", DBConstant.MONGO_COLLECTION_ALS_MOVIE_SIM)
    //    //      .format("json")
    //    //      .load()
    //    //      .rdd
    //    //      .count()
    //    //    println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cnt)

    // 获取连接
    val codecRegistry: CodecRegistry = fromRegistries(fromProviders(classOf[UserRecs], classOf[BaseRecommendation]), DEFAULT_CODEC_REGISTRY)
    val database: MongoDatabase = MongoClient(DBConstant.MONGO_SCALA_DRIVER_CONNECT_URL)
      .getDatabase(DBConstant.MONGO_DB)
      .withCodecRegistry(codecRegistry)
    val streamingRatingUserRecsCol: MongoCollection[UserRecs] = database.getCollection(DBConstant.MONGO_COLLECTION_STREAMING_RATING_USER_RECS)
    streamingRatingUserRecsCol.deleteOne(equal("user_id", 4)).printResults()



    //    spark.close()

  }


}
