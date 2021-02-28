package spark.service

import breeze.numerics.sqrt
import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.mllib.recommendation.{ALS, MatrixFactorizationModel, Rating}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import spark.constant.DBConstant
import spark.entity.MovieUserRatings

/**
 * ALS算法参数调优器
 *
 * @author PengChenyu
 * @since 2021-02-22 17:42:52
 */
object ALSTrainer {

  // 日志
  private val logger: Logger = Logger.getLogger(getClass)

  def main(args: Array[String]): Unit = {

    // 环境配置
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("ContentRecommeder")
    val spark: SparkSession = SparkSession.builder()
      .config(sparkConf)
      .config("spark.mongodb.input.uri", DBConstant.MONGO_URL)
      .config("spark.mongodb.output.uri", DBConstant.MONGO_URL)
      .getOrCreate()
    import spark.implicits._

    // 加载数据库中的评分数据
    val ratingRDD: RDD[Rating] = spark.read
      .format("jdbc")
      .option("url", DBConstant.MYSQL_URL)
      .option("driver", DBConstant.MYSQL_DRIVER)
      .option("user", DBConstant.MYSQL_USER)
      .option("password", DBConstant.MYSQL_PWD)
      .option("dbtable", DBConstant.MYSQL_TABLE_MOVIE_USER_RATINGS)
      .load()
      .as[MovieUserRatings]
      .rdd
      .map(rating => Rating(rating.user_id, rating.douban_id, rating.user_movie_rating))
      .cache()

    // 随机切分数据集，生成训练集和数据集
    val splitRDD: Array[RDD[Rating]] = ratingRDD.randomSplit(Array(0.8, 0.2))
    val trainRDD: RDD[Rating] = splitRDD(0)
    val testRDD: RDD[Rating] = splitRDD(1)

    // 参数调优
    // rank:特征维度
    // iteration:迭代次数
    // lambda:正则化参数
    val result: Array[(Int, Double, Double)] = for (rank <- Array(30, 40, 50, 60, 80); lambda <- Array(0.01, 0.3, 0.34, 0.38, 0.4, 1))
      yield {
        val model: MatrixFactorizationModel = ALS.train(trainRDD, rank, 3, lambda)
        val rmse: Double = getRMSE(model, testRDD)
        (rank, lambda, rmse)
      }

    // 查看最优参数
    logger.info(result.minBy(_._3))

    // 关闭环境
    spark.close()
  }

  // 计算均方根误差
  def getRMSE(model: MatrixFactorizationModel, data: RDD[Rating]): Double = {
    // 测试集不取原来的评分
    val userProducts: RDD[(Int, Int)] = data.map(item => (item.user, item.product))
    // 由训练集计算出的模型来计算预测评分
    val predictRating: RDD[Rating] = model.predict(userProducts)

    // 以(user_id，douban_id)作为外键，inner join实际观测值和预测值
    val observed: RDD[((Int, Int), Double)] = data.map(
      item => ((item.user, item.product), item.rating)
    )
    val predict: RDD[((Int, Int), Double)] = predictRating.map(
      item => ((item.user, item.product), item.rating)
    )
    sqrt(
      // 内连接得到((user_id, douban_id),(actual, predict))
      observed.join(predict).map {
        case ((user_id, douban_id), (actual, pre)) =>
          val err = actual - pre
          err * err
      }.mean()
    )
  }

}
