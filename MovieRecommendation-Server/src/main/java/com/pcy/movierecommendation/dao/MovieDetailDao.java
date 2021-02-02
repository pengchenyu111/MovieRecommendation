package com.pcy.movierecommendation.dao;

import com.pcy.movierecommendation.entity.movieDetail.MovieDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (MovieDetail)表数据库访问层
 *
 * @author PengChenyu
 * @since 2020-12-21 21:41:51
 */
@Repository
@Mapper
public interface MovieDetailDao {

    /**
     * 通过ID查询单条数据
     *
     * @param doubanId 主键
     * @return 实例对象
     */
    MovieDetail queryById(Integer doubanId);

    /**
     * 通过多个ID查询数据
     *
     * @param doubanIdList 主键列表
     * @return 对象列表
     */
    List<MovieDetail> queryByIdList(@Param("doubanIdList")List<Integer> doubanIdList);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<MovieDetail> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 查询所有数据
     *
     * @return 对象列表
     */
    List<MovieDetail> queryAllMovieDetails();


    /**
     * 通过实体作为筛选条件查询
     *
     * @param movieDetail 实例对象
     * @return 对象列表
     */
    List<MovieDetail> queryAll(MovieDetail movieDetail);


    /**
     * 查询总数据数
     *
     * @return 数据总数
     */
    int count();


    /**
     * 新增数据
     *
     * @param movieDetail 实例对象
     * @return 影响行数
     */
    int insert(MovieDetail movieDetail);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MovieDetail> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MovieDetail> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MovieDetail> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<MovieDetail> entities);

    /**
     * 修改数据
     *
     * @param movieDetail 实例对象
     * @return 影响行数
     */
    int update(MovieDetail movieDetail);

    /**
     * 通过主键删除数据
     *
     * @param doubanId 主键
     * @return 影响行数
     */
    int deleteById(Integer doubanId);

}