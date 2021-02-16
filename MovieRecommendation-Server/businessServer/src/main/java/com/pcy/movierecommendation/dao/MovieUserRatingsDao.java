package com.pcy.movierecommendation.dao;

import com.pcy.movierecommendation.entity.movieReviews.MovieUserRatings;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (MovieUserRatings)表数据库访问层
 *
 * @author PengChenyu
 * @since 2020-12-30 16:25:24
 */
@Repository
@Mapper
public interface MovieUserRatingsDao {

    /**
     * 通过ID查询单条数据
     *
     * @param reviewId 主键
     * @return 实例对象
     */
    MovieUserRatings queryById(String reviewId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<MovieUserRatings> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param movieUserRatings 实例对象
     * @return 对象列表
     */
    List<MovieUserRatings> queryAll(MovieUserRatings movieUserRatings);


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
     * @return 影响行数
     */
    int insert(MovieUserRatings movieUserRatings);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MovieUserRatings> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MovieUserRatings> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MovieUserRatings> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<MovieUserRatings> entities);

    /**
     * 修改数据
     *
     * @param movieUserRatings 实例对象
     * @return 影响行数
     */
    int update(MovieUserRatings movieUserRatings);

    /**
     * 通过主键删除数据
     *
     * @param reviewId 主键
     * @return 影响行数
     */
    int deleteById(String reviewId);

}