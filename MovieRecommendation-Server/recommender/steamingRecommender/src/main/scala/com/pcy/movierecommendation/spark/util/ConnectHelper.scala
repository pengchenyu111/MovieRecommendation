package com.pcy.movierecommendation.spark.util

import com.pcy.movierecommendation.spark.constant.RedisConstant
import redis.clients.jedis.{Jedis, JedisPool, JedisPoolConfig}

/**
 * 连接助手对象
 *
 * @author PengChenyu
 * @since 2021-03-01 20:44:34
 */
object ConnectHelper extends Serializable {


  val config = new JedisPoolConfig()
  config.setMaxWaitMillis(RedisConstant.REDIS_MAX_WAIT_MILLIS)
  val jedisPool = new JedisPool(
    config,
    RedisConstant.REDIS_SERVER_HOST,
    RedisConstant.REDIS_PORT,
    RedisConstant.REDIS_TIMEOUT,
    RedisConstant.REDIS_PASSWORD)
  lazy val jedis: Jedis = jedisPool.getResource
}
