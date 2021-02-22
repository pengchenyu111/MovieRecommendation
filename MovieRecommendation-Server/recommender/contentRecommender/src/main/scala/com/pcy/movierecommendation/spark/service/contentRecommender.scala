package com.pcy.movierecommendation.spark.service

import com.pcy.movierecommendation.spark.constant.DBConstant
import com.pcy.movierecommendation.spark.entity.{BaseRecommendation, MovieDetail, MovieRecs}
import com.pcy.movierecommendation.spark.util.MongoDBUtil
import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.ml.feature.{HashingTF, IDF, IDFModel, Tokenizer}
import org.apache.spark.ml.linalg.SparseVector
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import org.jblas.DoubleMatrix

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
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("ContentRecommeder")
    val spark: SparkSession = SparkSession.builder()
      .config(sparkConf)
      .config("spark.mongodb.input.uri", DBConstant.MONGO_URL)
      .config("spark.mongodb.output.uri", DBConstant.MONGO_URL)
      .getOrCreate()
    import spark.implicits._

    // 加载movie_detail表数据
    val movieDetailDS: Dataset[MovieDetail] = spark.read
      .format("jdbc")
      .option("url", DBConstant.MYSQL_URL)
      .option("driver", DBConstant.MYSQL_DRIVER)
      .option("user", DBConstant.MYSQL_USER)
      .option("password", DBConstant.MYSQL_PWD)
      .option("dbtable", DBConstant.MYSQL_TABLE_MOVIE_DETAIL)
      .load()
      .as[MovieDetail]

    // 预处理，保留部分字段
    val movieBriefDF: DataFrame = movieDetailDS
      .filter(x => !x.types.isEmpty)
      .map(
        x => (x.douban_id, x.title, x.types.replaceAll(" ", "").map(c => if (c == '/') ' ' else c))
      )
      .toDF("douban_id", "title", "types")
      .cache()

    // 创建分词器，默认按空格分词
    val tokenizer: Tokenizer = new Tokenizer().setInputCol("types").setOutputCol("words")
    // 用分词器对原始数据做转换，生成新的一列words
    val wordsDF: DataFrame = tokenizer.transform(movieBriefDF)
    // 引入HashingTF工具，把一个词语序列转化成对应的词频,目前数据库中有31种类型，这里暂且设置为50维度
    val hashingTF: HashingTF = new HashingTF().setInputCol("words").setOutputCol("rawFeatures").setNumFeatures(50)
    val typeCountDF: DataFrame = hashingTF.transform(wordsDF)
    // 引入IDF工具,得到每个词的逆文档频率
    val idf: IDF = new IDF().setInputCol("rawFeatures").setOutputCol("features")
    val idfModel: IDFModel = idf.fit(typeCountDF)
    // 用模型对原数据进行处理，得到文档中每个词的tf-idf，作为新的特征向量
    val rescaledDF: DataFrame = idfModel.transform(typeCountDF)

    // rescaledDF.show(10, false)

    // 转化类型
    val movieFeatures: RDD[(Int, DoubleMatrix)] = rescaledDF.map(
      row => (row.getAs[Int]("douban_id"), row.getAs[SparseVector]("features").toArray)
    )
      .rdd
      .map(
        x => (x._1, new DoubleMatrix(x._2))
      )

    // movieFeatures.collect().foreach(println)

    // 对所有电影两两计算它们的相似度，先做笛卡尔积
    val movieRecsDF: DataFrame = movieFeatures.cartesian(movieFeatures)
      .filter {
        // 把自己跟自己的配对过滤掉
        case (a, b) => a._1 != b._1
      }
      .map {
        case (a, b) => {
          val simScore: Double = this.cosSim(a._2, b._2)
          (a._1, (b._1, simScore))
        }
      }
      .filter(_._2._2 > 0.8) // 过滤出相似度大于0.8的
      .groupByKey()
      .map {
        case (douban_id, items) => MovieRecs(douban_id, items.toList.sortWith(_._2 > _._2).map(x => BaseRecommendation(x._1, x._2)))
      }
      .toDF()

    // movieRecsDF.show(10, truncate = false)

    // 存入结果到MongoDB
    MongoDBUtil.storeDFInMongoDB(movieRecsDF, DBConstant.MONGO_COLLECTION_CONTENT)

    // 关闭环境
    spark.close()
  }


  // 求向量余弦相似度
  def cosSim(movie1: DoubleMatrix, movie2: DoubleMatrix): Double = {
    movie1.dot(movie2) / (movie1.norm2() * movie2.norm2())
  }


}
