package com.pcy.movierecommendation.service;

import com.pcy.movierecommendation.entity.movieReviews.MovieUserRatings;

import java.util.List;

/**
 * (MovieUserRatings)表服务接口
 *
 * @author PengChenyu
 * @since 2020-12-30 16:25:24
 */
public interface MovieUserRatingsService {

    /**
     * 通过ID查询单条数据
     *
     * @param reviewId 主键
     * @return 实例对象
     */
    MovieUserRatings queryById(String reviewId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<MovieUserRatings> queryAllByLimit(int offset, int limit);


    /**
     * 查询总数据数
     *
     * @return 数据总数
     */
    int count();


    /**
     * 新增数据
     *
     * @param movieUserRatings 实例对象
     * @return 实例对象
     */
    MovieUserRatings insert(MovieUserRatings movieUserRatings);

    /**
     * 修改数据
     *
     * @param movieUserRatings 实例对象
     * @return 实例对象
     */
    MovieUserRatings update(MovieUserRatings movieUserRatings);

    /**
     * 通过主键删除数据
     *
     * @param reviewId 主键
     * @return 是否成功
     */
    boolean deleteById(String reviewId);

    /**
     * 获取用户最近的K次评分数据(简要信息)
     *
     * @param userId 用户id
     * @param k      数据量
     * @return 数据列表
     */
    List<MovieUserRatings> kRecentRatingsShort(Integer userId, Integer k);
}