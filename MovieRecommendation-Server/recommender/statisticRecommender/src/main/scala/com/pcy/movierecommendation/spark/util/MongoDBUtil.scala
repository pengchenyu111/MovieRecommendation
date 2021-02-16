package com.pcy.movierecommendation.spark.util

import org.apache.spark.sql.DataFrame

/**
 *
 * @author PengChenyu
 * @since 2021-02-02 19:20:12
 */
object MongoDBUtil {

  /**
   * 将DataFrame存入MongoDB
   * 要用到MongoSpark，官方文档：https://docs.mongodb.com/spark-connector/master/scala/datasets-and-sql
   *
   * @param df              数据
   * @param collection_name 文档名
   */
  def storeDFInMongoDB(df: DataFrame, collection_name: String): Unit = {
    import com.mongodb.spark._
    MongoSpark.save(
      df.write
        .option("collection", collection_name)
        .mode("overwrite"))
  }

}
