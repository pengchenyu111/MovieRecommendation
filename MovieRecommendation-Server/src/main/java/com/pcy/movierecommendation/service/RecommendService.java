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
}