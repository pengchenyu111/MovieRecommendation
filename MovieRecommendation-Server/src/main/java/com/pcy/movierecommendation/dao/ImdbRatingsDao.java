package com.pcy.movierecommendation.dao;

import com.pcy.movierecommendation.entity.imdbRatings.ImdbRatings;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (ImdbRatings)表数据库访问层
 *
 * @author PengChenyu
 * @since 2020-12-27 22:28:28
 */
@Repository
@Mapper
public interface ImdbRatingsDao {

    /**
     * 通过ID查询单条数据
     *
     * @param imdbId 主键
     * @return 实例对象
     */
    ImdbRatings queryById(String imdbId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ImdbRatings> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param imdbRatings 实例对象
     * @return 对象列表
     */
    List<ImdbRatings> queryAll(ImdbRatings imdbRatings);


    /**
     * 查询总数据数
     *
     * @return 数据总数
     */
    int count();


    /**
     * 新增数据
     *
     * @param imdbRatings 实例对象
     * @return 影响行数
     */
    int insert(ImdbRatings imdbRatings);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ImdbRatings> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ImdbRatings> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ImdbRatings> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<ImdbRatings> entities);

    /**
     * 修改数据
     *
     * @param imdbRatings 实例对象
     * @return 影响行数
     */
    int update(ImdbRatings imdbRatings);

    /**
     * 通过主键删除数据
     *
     * @param imdbId 主键
     * @return 影响行数
     */
    int deleteById(String imdbId);

}