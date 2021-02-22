package com.pcy.movierecommendation.spark.entity

/**
 *
 * @author PengChenyu
 * @since 2021-02-02 16:59:36
 */
case class MovieDetail(
                        douban_id: Int,
                        title: String,
                        brief_instruction: String,
                        directors: String,
                        screenwriters: String,
                        casts: String,
                        types: String,
                        production_country_area: String,
                        language: String,
                        publish_date: String,
                        runtime: String,
                        rating_score: Double,
                        rating_star: Int,
                        rating_num: Int,
                        rating_5_star_weight: String,
                        rating_4_star_weight: String,
                        rating_3_star_weight: String,
                        rating_2_star_weight: String,
                        rating_1_star_weight: String,
                        better_than: String,
                        douban_url: String,
                        cover_url: String,
                        imdb_url: String,
                        img_list: String
                      )
