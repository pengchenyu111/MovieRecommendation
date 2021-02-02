package com.pcy.movierecommendation.spark.service

import com.pcy.movierecommendation.core.constants.DBConstant
import com.pcy.movierecommendation.spark.util.MongoDBUtil
import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.slf4j.{Logger, LoggerFactory}


/**
 * 统计推荐服务
 *
 * @author PengChenyu
 * @since 2021-02-02 18:48:00
 */

object StatisticsRecommender {

  def main(args: Array[String]): Unit = {

    // 日志配置
    val logger: Logger = LoggerFactory.getLogger(getClass)
    // 环境配置
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("StatisticsRecommeder")
    val spark: SparkSession = SparkSession.builder()
      .config(sparkConf)
      .config("spark.mongodb.input.uri", DBConstant.MONGO_URL)
      .config("spark.mongodb.output.uri", DBConstant.MONGO_URL)
      .getOrCreate()
    import spark.implicits._


    // 从MySQL加载数据
    val df: DataFrame = spark.read
      .format("jdbc")
      .option("url", DBConstant.MYSQL_URL)
      .option("driver", DBConstant.MYSQL_DRIVER)
      .option("user", DBConstant.MYSQL_USER)
      .option("password", DBConstant.MYSQL_PWD)
      .option("dbtable", DBConstant.MYSQL_TABLE_MOVIE_DETAIL)
      .load()

    // 分析数据
    df.createOrReplaceTempView("movie")
    val resultDF: DataFrame = spark.sql(
      """
        |select * from movie order by rating_num DESC
        |""".stripMargin)
      .limit(20)
      .toDF()

    // 存入结果到MongoDB
    MongoDBUtil.storeDFInMongoDB(resultDF, DBConstant.MONGO_COLLECTION_HISTORY_TOP_20)
    logger.info("计算历史热门电影并存入MongoDB")

    //关闭环境
    spark.stop()
  }


}
