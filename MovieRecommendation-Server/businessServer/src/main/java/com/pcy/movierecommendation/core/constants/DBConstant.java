package com.pcy.movierecommendation.core.constants;

/**
 * 数据库常量
 *
 * @author PengChenyu
 * @since 2020-12-18 17:42:00
 */
public class DBConstant {

    /**
     * MongoDB配置
     */
    public static final String MONGO_URL = "mongodb://81.70.252.155:27017/recommender";
    public static final String MONGO_DB = "recommender";

    /**
     * 历史热门电影TOP20表
     */
    public static final String MONGO_COLLECTION_HISTORY_TOP_20 = "history_top_20";
    /**
     * 近期热门电影TOP表
     */
    public static final String MONGO_COLLECTION_RECENTLY_TOP = "recently_top";
    /**
     * 近期热门电影TOP表
     */
    public static final String MONGO_COLLECTION_GENRE_TOP = "genre_top";
    /**
     * 基于内容的电影推荐表
     */
    public static final String MONGO_COLLECTION_CONTENT = "content_top";
    /**
     * 基于ALS的用户推荐表
     */
    public static final String MONGO_COLLECTION_ALS_USER_RECS = "als_user_recs";
    /**
     * 基于ALS的电影相似度列表
     */
    public static final String MONGO_COLLECTION_ALS_MOVIE_SIM = "als_movie_sim";


    /**
     * MySQL配置
     */
    public static final String MYSQL_URL = "jdbc:mysql://81.70.252.155:3306/movie_recommendation?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true";
    public static final String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String MYSQL_USER = "root";
    public static final String MYSQL_PWD = "Pcy90321.";

    /**
     * 电影详情表
     */
    public static final String MYSQL_TABLE_MOVIE_DETAIL = "movie_detail";
    /**
     * 电影详情表
     */
    public static final String MYSQL_TABLE_MOVIE_TAG = "movie_tag";
    /**
     * 用户评分表
     */
    public static final String MYSQL_TABLE_MOVIE_USER_RATINGS = "movie_user_ratings";


}
