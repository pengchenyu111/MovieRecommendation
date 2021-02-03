package com.pcy.movierecommendation.spark.entity

/**
 * 电影类别top10推荐对象
 *
 * @author PengChenyu
 * @since 2021-02-03 15:35:54
 */
case class GenreTop10(genre: String, recommendations: Seq[BaseRecommendation])
