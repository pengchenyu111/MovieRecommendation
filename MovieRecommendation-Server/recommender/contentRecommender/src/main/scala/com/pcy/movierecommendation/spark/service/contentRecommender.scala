package com.pcy.movierecommendation.spark.service

import com.pcy.movierecommendation.spark.constant.DBConstant
import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

/**
 * 基于内容的推荐算法
 *
 * @author PengChenyu
 * @since 2021-02-21 16:20:40
 */
object contentRecommender {

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

    println("测试环境")

    // 关闭环境
    spark.close()
  }


}
