package com.pcy.movierecommendation.spark.constant

/**
 * 数据库常量
 *
 * @author PengChenyu
 * @since 2020-12-18 17:42:00
 */
object
DBConstant {

  /**
   * MongoDB配置
   */
  val MONGO_URL = "mongodb://pcy:Pcy548890321!,.@81.70.252.155:27017/recommender";
  val MONGO_DB = "recommender";

  /**
   * 历史热门电影TOP20表
   */
  val MONGO_COLLECTION_HISTORY_TOP_20 = "history_top_20";

  /**
   * 近期热门电影TOP表
   */
  val MONGO_COLLECTION_RECENTLY_TOP = "recently_top";

  /**
   * 近期热门电影TOP表
   */
  val MONGO_COLLECTION_GENRE_TOP = "genre_top";

  /**
   * 基于内容的电影推荐表
   */
  val MONGO_COLLECTION_CONTENT = "content_top";


  /**
   * MySQL配置
   */
  val MYSQL_URL = "jdbc:mysql://81.70.252.155/movie_recommendation?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true";
  val MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
  val MYSQL_USER = "root";
  val MYSQL_PWD = "Pcy90321.";

  /**
   * 电影详情表
   */
  val MYSQL_TABLE_MOVIE_DETAIL = "movie_detail";

  /**
   * 电影详情表
   */
  val MYSQL_TABLE_MOVIE_TAG = "movie_tag";

  /**
   * 用户评分表
   */
  val MYSQL_TABLE_MOVIE_USER_RATINGS = "movie_user_ratings";


}
