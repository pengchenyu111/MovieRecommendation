package spark.entity

/**
 * 用户的电影推荐列表
 *
 * @author PengChenyu
 * @since 2021-02-22 11:59:00
 */
case class UserRecs(user_id: Int, recs: Seq[BaseRecommendation])
