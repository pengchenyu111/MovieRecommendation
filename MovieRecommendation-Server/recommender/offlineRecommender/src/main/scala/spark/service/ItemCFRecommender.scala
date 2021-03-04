package spark.service

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}
import spark.constant.DBConstant
import spark.entity.{BaseRecommendation, MovieRecs, MovieUserRatings}
import spark.util.MongoDBUtil

/**
 * 基于物品的协同过滤相似推荐
 *
 * @author PengChenyu
 * @since 2021-03-01 16:58:27
 */
object ItemCFRecommender {

  def main(args: Array[String]): Unit = {
    // 环境配置
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("ItemCFRecommeder")
    val spark: SparkSession = SparkSession.builder()
      .config(sparkConf)
      .config("spark.mongodb.input.uri", DBConstant.MONGO_URL)
      .config("spark.mongodb.output.uri", DBConstant.MONGO_URL)
      .getOrCreate()
    import spark.implicits._

    // 加载数据库中的评分数据
    val ratingDF: DataFrame = spark.read
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
      .toDF("userId", "productId", "rating")

    // 统计每个电影的评分个数，并通过内连接添加到 ratingDF 中
    val numRatersPerProduct: DataFrame = ratingDF.groupBy("productId").count()
    val ratingWithCountDF: DataFrame = ratingDF.join(numRatersPerProduct, "productId")

    // 将电影评分按 userId 两两配对，可以统计两个商品被同一用户做出评分的次数
    val joinedDF: DataFrame = ratingWithCountDF
      .join(ratingWithCountDF, "userId")
      .toDF("userId", "product1", "rating1", "count1", "product2", "rating2", "count2")
      .select("userId", "product1", "count1", "product2", "count2")
    joinedDF.createOrReplaceTempView("joined")

    val coOccurrenceDF: DataFrame = spark.sql(
      """
        |select product1
        |, product2
        |, count(userId) as coocount
        |, first(count1) as count1
        |, first(count2) as count2
        |from joined
        |group by product1, product2
      """.stripMargin
    ).cache()

    // 用同现的次数和各自的次数，计算同现相似度
    val itemCF: DataFrame = coOccurrenceDF.map {
      row =>
        val coSim: Double = coOccurrenceSim(row.getAs[Long]("coocount"), row.getAs[Long]("count1"), row.getAs[Long]("count2"))
        (row.getAs[Int]("product1"), (row.getAs[Int]("product2"), coSim))
    }
      .rdd
      .groupByKey()
      .map {
        case (productId, recs) =>
          MovieRecs(productId, recs.toList.filter(x => x._1 != productId).sortWith(_._2 > _._2).map(x => BaseRecommendation(x._1, x._2)).take(20))
      }
      .toDF()

    // 存入MongoDB
    MongoDBUtil.storeDFInMongoDB(itemCF,DBConstant.MONGO_COLLECTION_ITEM_CF_MOVIE_RECS)

    // 关闭
    spark.close()
  }

  /**
   * 同现相似度
   *
   * @param cooCount 共同评分的受众
   * @param count1   物品1的评分受众
   * @param count2   物品2的评分受众
   * @return
   */
  def coOccurrenceSim(cooCount: Long, count1: Long, count2: Long): Double = {
    cooCount / math.sqrt(count1 * count2)
  }


}
