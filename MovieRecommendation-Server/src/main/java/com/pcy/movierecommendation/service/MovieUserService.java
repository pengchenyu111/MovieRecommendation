package com.pcy.movierecommendation.service;

import com.github.pagehelper.PageInfo;
import com.pcy.movierecommendation.entity.movieUser.MovieUser;

import java.util.List;

/**
 * (MovieUser)表服务接口
 *
 * @author PengChenyu
 * @since 2020-12-18 17:42:00
 */
public interface MovieUserService {

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    MovieUser queryById(Integer userId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<MovieUser> queryAllByLimit(int offset, int limit);

    /**
     * 分页查询
     *
     * @param pageNum   当前页
     * @param pageSize  每页的数量
     * @param movieUser 查询条件
     * @return 分页信息
     */
    PageInfo<MovieUser> queryPage(int pageNum, int pageSize, MovieUser movieUser);


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
     * @return 实例对象
     */
    MovieUser insert(MovieUser movieUser);

    /**
     * 修改数据
     *
     * @param movieUser 实例对象
     * @return 实例对象
     */
    MovieUser update(MovieUser movieUser);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer userId);

    /**
     * 用户通过账号和密码登录
     *
     * @param account  账号
     * @param password 密码
     * @return 实例对象
     */
    MovieUser login(String account, String password);

    /**
     * 用户修改密码
     *
     * @param account         账户
     * @param verifyCode      验证码
     * @param newPassword     新密码
     * @param confirmPassword 确认密码
     * @return 实例对象
     */
    MovieUser changePassword(String account, String verifyCode, String newPassword, String confirmPassword);

    /**
     * 用户注册
     *
     * @param movieUser 要注册的用户信息
     * @return 注册标识
     */
    MovieUser register(MovieUser movieUser);

    /**
     * 通过user_unique_name查询用户信息
     *
     * @param userUniqueName 用户唯一名
     * @return 单条数据
     */
    MovieUser queryByUserUniqueName(String userUniqueName);
}