package com.pcy.movierecommendation.spark.entity

import org.mongodb.scala.bson.ObjectId

/**
 * 用户的电影推荐列表
 *
 * @author PengChenyu
 * @since 2021-02-22 11:59:00
 */
//object UserRecs {
//  def apply(user_id: Int, recs: Seq[BaseRecommendation]): UserRecs =
//    UserRecs(user_id, recs)
//}

case class UserRecs(user_id: Int, recs: Seq[BaseRecommendation]) extends Serializable
