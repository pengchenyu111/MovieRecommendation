package com.pcy.movierecommendation.spark.entity

/**
 * 基准推荐对象
 *
 * @author PengChenyu
 * @since 2021-02-03 15:36:37
 */
//object BaseRecommendation {
//  def apply(id: Int, score: Double): UserRecs =
//    BaseRecommendation(id, score)
//}
case class BaseRecommendation(id: Int, score: Double) extends Serializable
