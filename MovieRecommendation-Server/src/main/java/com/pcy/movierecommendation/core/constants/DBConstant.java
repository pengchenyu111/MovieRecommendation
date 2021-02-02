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
    public static final String MONGO_URL = "mongodb://49.232.218.99:27017/recommender";
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
     * MySQL配置
     */
    public static final String MYSQL_URL = "jdbc:mysql://49.232.218.99:3306/movie_recommendation?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true";
    public static final String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String MYSQL_USER = "root";
    public static final String MYSQL_PWD = "Pcy90321.";

    /**
     * 电影详情表
     */
    public static final String MYSQL_TABLE_MOVIE_DETAIL = "movie_detail";
    /**
     * 用户评分表
     */
    public static final String MYSQL_TABLE_MOVIE_USER_RATINGS = "movie_user_ratings";


}
