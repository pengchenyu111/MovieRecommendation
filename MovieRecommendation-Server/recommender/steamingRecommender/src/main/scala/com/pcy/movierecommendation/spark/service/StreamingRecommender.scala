package com.pcy.movierecommendation.spark.service

import com.pcy.movierecommendation.spark.constant.{DBConstant, KafkaConstant}
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 *
 * 实时推荐服务
 *
 * @author PengChenyu
 * @since 2021-02-28 22:08:22
 */
object StreamingRecommender {

  def main(args: Array[String]): Unit = {
    // 环境配置
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("StreamingRecommeder")
    val spark: SparkSession = SparkSession.builder()
      .config(sparkConf)
      .config("spark.mongodb.input.uri", DBConstant.MONGO_URL)
      .config("spark.mongodb.output.uri", DBConstant.MONGO_URL)
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext
    val ssc = new StreamingContext(sc, Seconds(3))
    import spark.implicits._

    // Kafka配置
    val kafkaParam: Map[String, Object] = Map[String, Object](
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> KafkaConstant.BOOTSTRAP_SERVERS_CONFIG,
      ConsumerConfig.GROUP_ID_CONFIG -> KafkaConstant.GROUP_ID,
      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG -> KafkaConstant.SERIALIZER,
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG -> KafkaConstant.SERIALIZER,
      ConsumerConfig.AUTO_OFFSET_RESET_CONFIG -> KafkaConstant.AUTO_OFFSET_RESET
    )
    // 通过kafka创建一个DStream
    val kafkaStream: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream[String, String](
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](Set(KafkaConstant.TOPIC_RATING), kafkaParam)
    )

    kafkaStream.map(_.value()).print()


    // 启动
    ssc.start()

    // 等待处理关闭
    ssc.awaitTermination()
  }

}
