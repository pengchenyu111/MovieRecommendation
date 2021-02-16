package com.pcy.movierecommendation.dao;

import com.pcy.movierecommendation.entity.movieTag.UserTagPrefer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (UserTagPrefer)表数据库访问层
 *
 * @author PengChenyu
 * @since 2021-02-03 21:32:32
 */
@Repository
@Mapper
public interface UserTagPreferDao {

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    UserTagPrefer queryById(Integer userId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<UserTagPrefer> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param userTagPrefer 实例对象
     * @return 对象列表
     */
    List<UserTagPrefer> queryAll(UserTagPrefer userTagPrefer);


    /**
     * 查询总数据数
     *
     * @return 数据总数
     */
    int count();


    /**
     * 新增数据
     *
     * @param userTagPrefer 实例对象
     * @return 影响行数
     */
    int insert(UserTagPrefer userTagPrefer);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<UserTagPrefer> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<UserTagPrefer> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<UserTagPrefer> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<UserTagPrefer> entities);

    /**
     * 修改数据
     *
     * @param userTagPrefer 实例对象
     * @return 影响行数
     */
    int update(UserTagPrefer userTagPrefer);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 影响行数
     */
    int deleteById(Integer userId);

}