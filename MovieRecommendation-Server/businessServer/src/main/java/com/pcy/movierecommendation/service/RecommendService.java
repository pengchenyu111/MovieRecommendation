package com.pcy.movierecommendation.service;

import com.pcy.movierecommendation.entity.movieDetail.MovieDetail;

import java.util.List;

/**
 * 推荐服务
 *
 * @author PengChenyu
 * @since 2021-02-02 15:12:57
 */
public interface RecommendService {

    /**
     * 历史热门Top20电影
     *
     * @return 对象列表
     */
    List<MovieDetail> historyTop20();


    /**
     * 近期热门Top20电影
     *
     * @return 对象列表
     */
    List<MovieDetail> recentlyTop20();


    /**
     * 分类Top10电影
     *
     * @param genre 分类名
     * @return 对象列表
     */
    List<MovieDetail> genreTop10(String genre);


    /**
     * 多分类综合Top10
     *
     * @param genreList 分类列表
     * @return 对象列表
     */
    List<MovieDetail> genreCompositeTop10(List<String> genreList);

    /**
     * 用户感兴趣标签推荐Top10
     *
     * @param userId 用户id
     * @return 对象列表
     */
    List<MovieDetail> userPreferGenreTop10(Integer userId);

    /**
     * 基于内容的TF-IDF电影推荐
     *
     * @param doubanId 豆瓣id
     * @return 对象列表
     */
    List<MovieDetail> contentTFIDF(Integer doubanId);

    /**
     * 基于ALS的用户电影推荐
     *
     * @param userId 用户id
     * @return 推荐列表
     */
    List<MovieDetail> alsUserRecs(Integer userId);

    /**
     * 基于ALS的电影相似度推荐
     *
     * @param doubanId 豆瓣id
     * @return 推荐列表
     */
    List<MovieDetail> alsMovieSimRecs(Integer doubanId);
}
