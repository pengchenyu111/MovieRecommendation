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
}
