package com.pcy.movierecommendation.spark.constant

/**
 * Kafka配置参数
 *
 * @author PengChenyu
 * @since 2021-02-28 22:29:14
 */
object KafkaConstant {

  /**
   * 服务器地址
   */
  val BOOTSTRAP_SERVERS_CONFIG = "81.70.252.155:9092"

  /**
   * 序列化
   */
  val SERIALIZER = "org.apache.kafka.common.serialization.StringDeserializer"

  /**
   * 消费组
   */
  val GROUP_ID = "recommender"

  /**
   * 消费偏移量位置
   */
  val AUTO_OFFSET_RESET = "latest"

  /**
   * 评论Topic
   */
  val TOPIC_RATING = "movie_rec_sys_rating"

}
