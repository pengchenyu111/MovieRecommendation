package com.pcy.movierecommendation.spark.entity

/**
 * 用户评分样例类
 *
 * @author PengChenyu
 * @since 2021-02-22 17:52:00
 */
case class MovieUserRatings(reviewId: String,
                            doubanId: Int,
                            userId: Int,
                            userMovieRating: Double,
                            userMovieRatingTime: String)
