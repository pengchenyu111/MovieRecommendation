package com.pcy.movierecommendation.service;

import com.github.pagehelper.PageInfo;
import com.pcy.movierecommendation.entity.movieReviews.MovieReviews;

import java.util.List;

/**
 * (MovieReviews)表服务接口
 *
 * @author PengChenyu
 * @since 2020-12-29 20:08:18
 */
public interface MovieReviewsService {

    /**
     * 通过ID查询单条数据
     *
     * @param reviewId 主键
     * @return 实例对象
     */
    MovieReviews queryById(String reviewId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<MovieReviews> queryAllByLimit(int offset, int limit);


    /**
     * 查询总数据数
     *
     * @return 数据总数
     */
    int count();


    /**
     * 新增数据
     *
     * @param movieReviews 实例对象
     * @return 实例对象
     */
    MovieReviews insert(MovieReviews movieReviews);

    /**
     * 修改数据
     *
     * @param movieReviews 实例对象
     * @return 实例对象
     */
    MovieReviews update(MovieReviews movieReviews);

    /**
     * 通过主键删除数据
     *
     * @param reviewId 主键
     * @return 是否成功
     */
    boolean deleteById(String reviewId);

    /**
     * 根据doubanId分页查询
     *
     * @param doubanId 豆瓣id
     * @param pageNum  当前页
     * @param pageSize 每页多少数据
     * @return 分页数据
     */
    PageInfo<MovieReviews> queryByDoubanIdPage(Integer doubanId, Integer pageNum, Integer pageSize);

    /**
     * 用户评论
     *
     * @param movieReviews 评论信息
     * @return 评论信息
     */
    MovieReviews review(MovieReviews movieReviews);

    /**
     * 用户给评论点赞
     *
     * @param reviewId 评论id
     * @return 评论点赞是否成功
     */
    Boolean agree(String reviewId);
}