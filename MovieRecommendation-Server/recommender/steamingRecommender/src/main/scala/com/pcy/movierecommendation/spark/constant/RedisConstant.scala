package com.pcy.movierecommendation.spark.constant

/**
 * Redis配置参数
 *
 * @author PengChenyu
 * @since 2021-03-07 22:29:14
 */
object RedisConstant {

  /**
   * 服务器地址
   */
  val REDIS_SERVER_HOST = "81.70.252.155"

  /**
   * 端口
   */
  val REDIS_PORT = 6379

  /**
   * 超时时间
   */
  val REDIS_TIMEOUT = 5000

  /**
   * 密码
   */
  val REDIS_PASSWORD = "Pcy903215488"

  /**
   * 最大等待时长
   */
  val REDIS_MAX_WAIT_MILLIS = 10000

}
