package com.pcy.movierecommendation.dao;

import com.pcy.movierecommendation.entity.movieTag.MovieTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (MovieTag)表数据库访问层
 *
 * @author PengChenyu
 * @since 2021-02-01 14:34:51
 */
@Repository
@Mapper
public interface MovieTagDao {

    /**
     * 查询所有数据
     *
     * @return 实例对象
     */
    List<MovieTag> queryAllTags();

    /**
     * 通过ID查询单条数据
     *
     * @param tagId 主键
     * @return 实例对象
     */
    MovieTag queryById(Integer tagId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<MovieTag> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param movieTag 实例对象
     * @return 对象列表
     */
    List<MovieTag> queryAll(MovieTag movieTag);


    /**
     * 查询总数据数
     *
     * @return 数据总数
     */
    int count();


    /**
     * 新增数据
     *
     * @param movieTag 实例对象
     * @return 影响行数
     */
    int insert(MovieTag movieTag);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MovieTag> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MovieTag> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MovieTag> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<MovieTag> entities);

    /**
     * 修改数据
     *
     * @param movieTag 实例对象
     * @return 影响行数
     */
    int update(MovieTag movieTag);

    /**
     * 通过主键删除数据
     *
     * @param tagId 主键
     * @return 影响行数
     */
    int deleteById(Integer tagId);

}