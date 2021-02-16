package com.pcy.movierecommendation.dao;

import com.pcy.movierecommendation.entity.movieUser.MovieUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (MovieUser)表数据库访问层
 *
 * @author PengChenyu
 * @since 2020-12-18 17:42:00
 */
@Repository
@Mapper
public interface MovieUserDao {

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    MovieUser queryById(Integer userId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<MovieUser> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 直接查询全部数据
     *
     * @return 对象列表
     */
    List<MovieUser> queryAllUsers();


    /**
     * 通过实体作为筛选条件查询
     *
     * @param movieUser 实例对象
     * @return 对象列表
     */
    List<MovieUser> queryAll(MovieUser movieUser);


    /**
     * 查询总数据数
     *
     * @return 数据总数
     */
    int count();


    /**
     * 新增数据
     *
     * @param movieUser 实例对象
     * @return 影响行数
     */
    int insert(MovieUser movieUser);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MovieUser> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MovieUser> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MovieUser> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<MovieUser> entities);

    /**
     * 修改数据
     *
     * @param movieUser 实例对象
     * @return 影响行数
     */
    int update(MovieUser movieUser);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 影响行数
     */
    int deleteById(Integer userId);

    /**
     * 用户通过账号和密码登录
     *
     * @param account  账号
     * @param password 密码
     * @return 实例对象
     */
    MovieUser login(@Param("account") String account, @Param("password") String password);

    /**
     * 用户修改密码
     *
     * @param account     账号
     * @param newPassword 密码
     * @return 影响行数
     */
    int changePassword(@Param("account") String account, @Param("newPassword") String newPassword);

    /**
     * 通过user_unique_name查询用户信息
     *
     * @param userUniqueName 用户唯一名
     * @return 单条数据
     */
    MovieUser queryByUserUniqueName(String userUniqueName);
}