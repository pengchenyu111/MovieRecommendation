package spark.entity

/**
 * 用户评分样例类
 *
 * @author PengChenyu
 * @since 2021-02-22 17:52:00
 */
case class MovieUserRatings(review_id: String,
                            douban_id: Int,
                            user_id: Int,
                            user_movie_rating: Double,
                            user_movie_rating_time: String)
