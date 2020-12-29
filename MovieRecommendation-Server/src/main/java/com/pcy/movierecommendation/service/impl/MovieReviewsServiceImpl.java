package com.pcy.movierecommendation.service.impl;

import com.pcy.movierecommendation.dao.MovieReviewsDao;
import com.pcy.movierecommendation.entity.movieReviews.MovieReviews;
import com.pcy.movierecommendation.service.MovieReviewsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (MovieReviews)表服务实现类
 *
 * @author PengChenyu
 * @since 2020-12-29 20:08:19
 */
@Service("movieReviewsService")
public class MovieReviewsServiceImpl implements MovieReviewsService {
    @Resource
    private MovieReviewsDao movieReviewsDao;

    /**
     * 通过ID查询单条数据
     *
     * @param reviewId 主键
     * @return 实例对象
     */
    @Override
    public MovieReviews queryById(String reviewId) {
        return this.movieReviewsDao.queryById(reviewId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<MovieReviews> queryAllByLimit(int offset, int limit) {
        return this.movieReviewsDao.queryAllByLimit(offset, limit);
    }


    /**
     * 查询总数据数
     *
     * @return 数据总数
     */
    @Override
    public int count() {
        return this.movieReviewsDao.count();
    }


    /**
     * 新增数据
     *
     * @param movieReviews 实例对象
     * @return 实例对象
     */
    @Override
    public MovieReviews insert(MovieReviews movieReviews) {
        this.movieReviewsDao.insert(movieReviews);
        return movieReviews;
    }

    /**
     * 修改数据
     *
     * @param movieReviews 实例对象
     * @return 实例对象
     */
    @Override
    public MovieReviews update(MovieReviews movieReviews) {
        this.movieReviewsDao.update(movieReviews);
        return this.queryById(movieReviews.getReviewId());
    }

    /**
     * 通过主键删除数据
     *
     * @param reviewId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String reviewId) {
        return this.movieReviewsDao.deleteById(reviewId) > 0;
    }
}