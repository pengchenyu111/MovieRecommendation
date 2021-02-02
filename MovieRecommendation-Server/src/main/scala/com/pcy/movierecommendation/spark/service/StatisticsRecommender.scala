package com.pcy.movierecommendation.spark.service

import java.text.SimpleDateFormat
import java.util.Date

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
    //historyTop20()
    //recentlyTop()
  }

  /**
   * 环境配置
   *
   * @return
   */
  private def initEnv = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("StatisticsRecommeder")
    val spark: SparkSession = SparkSession.builder()
      .config(sparkConf)
      .config("spark.mongodb.input.uri", DBConstant.MONGO_URL)
      .config("spark.mongodb.output.uri", DBConstant.MONGO_URL)
      .getOrCreate()
    import spark.implicits._
    spark
  }

  /**
   * 计算历史热门电影Top20
   *
   */
  def historyTop20(): Unit = {
    val spark: SparkSession = initEnv
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

    // 关闭环境
    spark.close()
  }

  /**
   * 计算近期热门电影Top
   * douban_id | count | yearmonth
   */
  def recentlyTop(): Unit = {
    val spark: SparkSession = initEnv
    // 从MySQL加载数据
    val df: DataFrame = spark.read
      .format("jdbc")
      .option("url", DBConstant.MYSQL_URL)
      .option("driver", DBConstant.MYSQL_DRIVER)
      .option("user", DBConstant.MYSQL_USER)
      .option("password", DBConstant.MYSQL_PWD)
      .option("dbtable", DBConstant.MYSQL_TABLE_MOVIE_USER_RATINGS)
      .load()
    df.createOrReplaceTempView("movie_user_ratings")

    // 转换日期格式为yyyyMM
    val format = new SimpleDateFormat("yyyyMM")
    spark.udf.register("changeDate", (x: String) => format.format(format.parse(x)))

    // 预处理
    spark.sql(
      """
        |select douban_id, changeDate(user_movie_rating_time) as yearmonth from movie_user_ratings
        |""".stripMargin)
      .createOrReplaceTempView("ratingOfMonth")

    // 从ratingOfMonth中查找电影在各个月份的评分，mid，count，yearmonth
    val resultDF: DataFrame = spark.sql(
      """
        |select douban_id, count(douban_id) as count, yearmonth
        |from ratingOfMonth
        |group by yearmonth, douban_id
        |order by yearmonth desc, count desc
        |""".stripMargin)
      .toDF()

    // 存入结果到MongoDB
    MongoDBUtil.storeDFInMongoDB(resultDF, DBConstant.MONGO_COLLECTION_RECENTLY_TOP)

    // 关闭环境
    spark.close()
  }
}
