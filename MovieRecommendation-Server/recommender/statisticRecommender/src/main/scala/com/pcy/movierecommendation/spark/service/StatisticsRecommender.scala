package com.pcy.movierecommendation.spark.service

import java.text.SimpleDateFormat

import com.pcy.movierecommendation.spark.constant.DBConstant
import com.pcy.movierecommendation.spark.entity.{BaseRecommendation, GenreTop10}
import com.pcy.movierecommendation.spark.util.MongoDBUtil
import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Row, SparkSession}


/**
 * 统计推荐服务
 *
 * @author PengChenyu
 * @since 2021-02-02 18:48:00
 */

object StatisticsRecommender {

  // 日志
  private val logger: Logger = Logger.getLogger(getClass)

  def main(args: Array[String]): Unit = {

    // 环境配置
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("StatisticsRecommeder")
    val spark: SparkSession = SparkSession.builder()
      .config(sparkConf)
      .config("spark.mongodb.input.uri", DBConstant.MONGO_URL)
      .config("spark.mongodb.output.uri", DBConstant.MONGO_URL)
      .getOrCreate()
    import spark.implicits._

    // 统计服务算法
    historyTop20(spark)
    recentlyTop(spark)
    genreTop10(spark)

    // 关闭环境
    spark.close()
  }


  /**
   * 计算历史热门电影Top20
   *
   */
  def historyTop20(spark: SparkSession): Unit = {
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
    df.createOrReplaceTempView("movie_detail")
    val resultDF: DataFrame = spark.sql(
      """
        |select * from movie_detail order by rating_num DESC
        |""".stripMargin)
      .limit(20)
      .toDF()

    // 存入结果到MongoDB
    MongoDBUtil.storeDFInMongoDB(resultDF, DBConstant.MONGO_COLLECTION_HISTORY_TOP_20)
    logger.info("【historyTop20】计算完毕")
  }

  /**
   * 计算近期热门电影Top
   * douban_id | count | yearmonth
   */
  def recentlyTop(spark: SparkSession): Unit = {
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
    logger.info("【recentlyTop】计算完毕")
  }

  /**
   * 计算给类别电影Top10
   */
  def genreTop10(spark: SparkSession): Unit = {
    // 从MySQL加载数据
    val tagDF: DataFrame = spark.read
      .format("jdbc")
      .option("url", DBConstant.MYSQL_URL)
      .option("driver", DBConstant.MYSQL_DRIVER)
      .option("user", DBConstant.MYSQL_USER)
      .option("password", DBConstant.MYSQL_PWD)
      .option("dbtable", DBConstant.MYSQL_TABLE_MOVIE_TAG)
      .load()
    tagDF.createOrReplaceTempView("movie_tag")

    val movieDetailDF: DataFrame = spark.read
      .format("jdbc")
      .option("url", DBConstant.MYSQL_URL)
      .option("driver", DBConstant.MYSQL_DRIVER)
      .option("user", DBConstant.MYSQL_USER)
      .option("password", DBConstant.MYSQL_PWD)
      .option("dbtable", DBConstant.MYSQL_TABLE_MOVIE_DETAIL)
      .load()
    movieDetailDF.createOrReplaceTempView("movie_detail")

    // 提取分类标签
    val genresRDD: RDD[Row] = spark.sql(
      """
        |select tag_name from movie_tag
        |""".stripMargin)
      .rdd

    // 提起电影信息，过滤types为空的电影
    val movieRDD: RDD[Row] = spark.sql(
      """
        |select douban_id, rating_score, types from movie_detail
        |""".stripMargin)
      .rdd
      .filter(!_.getAs[String]("types").isEmpty)

    // 电影信息和类别做笛卡尔积
    val genresMovieRDD: RDD[(Row, Row)] = genresRDD.cartesian(movieRDD)

    import spark.implicits._

    // 开始计算各类别Top10
    val resultDF: DataFrame = genresMovieRDD
      .filter {
        // 过滤出电影类别包含该类别的电影
        case (genreRow, movieRow) => movieRow.getAs[String]("types").contains(genreRow.getAs[String]("tag_name"))
      }
      .map {
        // 转为(剧情,(12589643,8.3))的格式
        case (genreRow, movieRow) => (genreRow.getAs[String]("tag_name"), (movieRow.getAs[Int]("douban_id"), movieRow.getAs[Double]("rating_score")))
      }
      .groupByKey()
      .map {
        case (genre, iter) => GenreTop10(
          genre,
          iter.toList.sortWith(_._2 > _._2).take(10).map(item => BaseRecommendation(item._1, item._2))
        )
      }
      .toDF()

    // 存入结果到MongoDB
    MongoDBUtil.storeDFInMongoDB(resultDF, DBConstant.MONGO_COLLECTION_GENRE_TOP)
    logger.info("【genreTop10】计算完毕")
  }
}
