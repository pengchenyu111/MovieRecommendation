package com.pcy.movierecommendation.spark.entity

/**
 *
 * @author PengChenyu
 * @since 2021-02-22 11:59:00
 */
case class MovieRecs( douban_id: Int, recs: Seq[BaseRecommendation] )
