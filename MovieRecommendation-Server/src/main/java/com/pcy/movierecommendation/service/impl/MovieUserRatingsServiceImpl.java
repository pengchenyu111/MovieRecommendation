package com.pcy.movierecommendation.service.impl;

import com.pcy.movierecommendation.dao.MovieUserRatingsDao;
import com.pcy.movierecommendation.entity.movieReviews.MovieUserRatings;
import com.pcy.movierecommendation.service.MovieUserRatingsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (MovieUserRatings)表服务实现类
 *
 * @author PengChenyu
 * @since 2020-12-30 16:25:24
 */
@Service("movieUserRatingsService")
public class MovieUserRatingsServiceImpl implements MovieUserRatingsService {
    @Resource
    private MovieUserRatingsDao movieUserRatingsDao;

    /**
     * 通过ID查询单条数据
     *
     * @param reviewId 主键
     * @return 实例对象
     */
    @Override
    public MovieUserRatings queryById(String reviewId) {
        return this.movieUserRatingsDao.queryById(reviewId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<MovieUserRatings> queryAllByLimit(int offset, int limit) {
        return this.movieUserRatingsDao.queryAllByLimit(offset, limit);
    }


    /**
     * 查询总数据数
     *
     * @return 数据总数
     */
    @Override
    public int count() {
        return this.movieUserRatingsDao.count();
    }


    /**
     * 新增数据
     *
     * @param movieUserRatings 实例对象
     * @return 实例对象
     */
    @Override
    public MovieUserRatings insert(MovieUserRatings movieUserRatings) {
        this.movieUserRatingsDao.insert(movieUserRatings);
        return movieUserRatings;
    }

    /**
     * 修改数据
     *
     * @param movieUserRatings 实例对象
     * @return 实例对象
     */
    @Override
    public MovieUserRatings update(MovieUserRatings movieUserRatings) {
        this.movieUserRatingsDao.update(movieUserRatings);
        return this.queryById(movieUserRatings.getReviewId());
    }

    /**
     * 通过主键删除数据
     *
     * @param reviewId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String reviewId) {
        return this.movieUserRatingsDao.deleteById(reviewId) > 0;
    }
}