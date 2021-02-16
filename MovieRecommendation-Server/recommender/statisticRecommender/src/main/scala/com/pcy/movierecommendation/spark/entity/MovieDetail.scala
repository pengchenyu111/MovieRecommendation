package com.pcy.movierecommendation.spark.entity

/**
 *
 * @author PengChenyu
 * @since 2021-02-02 16:59:36
 */
case class MovieDetail(
                        doubanId: Int,
                        title: String,
                        briefInstruction: String,
                        directors: String,
                        screenwriters: String,
                        casts: String,
                        types: String,
                        productionCountryArea: String,
                        language: String,
                        publishDate: String,
                        runtime: String,
                        ratingScore: Double,
                        ratingStar: Int,
                        ratingNum: Int,
                        rating5StarWeight: String,
                        rating4StarWeight: String,
                        rating3StarWeight: String,
                        rating2StarWeight: String,
                        rating1StarWeight: String,
                        betterThan: String,
                        doubanUrl: String,
                        coverUrl: String,
                        imdbUrl: String,
                        imgList: String
                      )
