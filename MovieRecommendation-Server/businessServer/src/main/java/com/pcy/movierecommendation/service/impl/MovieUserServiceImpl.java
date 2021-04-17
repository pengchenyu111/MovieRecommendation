package com.pcy.movierecommendation.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pcy.movierecommendation.core.constants.ErrorMessages;
import com.pcy.movierecommendation.core.utils.DateFormatUtil;
import com.pcy.movierecommendation.core.utils.EncryptionUtil;
import com.pcy.movierecommendation.core.utils.IdWorkerUtil;
import com.pcy.movierecommendation.core.utils.RedisUtil;
import com.pcy.movierecommendation.dao.MovieUserDao;
import com.pcy.movierecommendation.entity.movieUser.MovieUser;
import com.pcy.movierecommendation.service.MovieUserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (MovieUser)表服务实现类
 *
 * @author PengChenyu
 * @since 2020-12-18 17:42:00
 */
@Service("movieUserService")
public class MovieUserServiceImpl implements MovieUserService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    RedisUtil redisUtil;
    @Resource
    private MovieUserDao movieUserDao;

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    @Override
    public MovieUser queryById(Integer userId) {
        return this.movieUserDao.queryById(userId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<MovieUser> queryAllByLimit(int offset, int limit) {
        return this.movieUserDao.queryAllByLimit(offset, limit);
    }

    /**
     * 分页查询
     *
     * @param pageNum   当前页
     * @param pageSize  每页的数量
     * @param movieUser 查询条件
     * @return 分页信息
     */
    @Override
    public PageInfo<MovieUser> queryPage(int pageNum, int pageSize, MovieUser movieUser) {
        PageHelper.startPage(pageNum, pageSize);
        List<MovieUser> movieUserList = movieUserDao.queryAll(movieUser);
        return new PageInfo<>(movieUserList);
    }


    /**
     * 查询总数据数
     *
     * @return 数据总数
     */
    @Override
    public int count() {
        return this.movieUserDao.count();
    }


    /**
     * 新增数据
     *
     * @param movieUser 实例对象
     * @return 实例对象
     */
    @Override
    public MovieUser insert(MovieUser movieUser) {
        this.movieUserDao.insert(movieUser);
        return movieUser;
    }

    /**
     * 修改数据
     *
     * @param movieUser 实例对象
     * @return 实例对象
     */
    @Override
    public MovieUser update(MovieUser movieUser) {
        movieUser.setAge(DateFormatUtil.getAgeByBirth(movieUser.getBirth()));
        this.movieUserDao.update(movieUser);
        return this.queryById(movieUser.getUserId());
    }

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer userId) {
        return this.movieUserDao.deleteById(userId) > 0;
    }

    /**
     * 用户通过账号和密码登录
     *
     * @param account  账号
     * @param password 密码
     * @return 实例对象
     */
    @Override
    public MovieUser login(String account, String password) {
        // 进行加盐加密
        String encryptionPassword = EncryptionUtil.sha384HashWithSalt(password);
        return this.movieUserDao.login(account, encryptionPassword);
    }

    /**
     * 用户修改密码
     *
     * @param account         账户
     * @param verifyCode      验证码
     * @param newPassword     新密码
     * @param confirmPassword 确认密码
     * @return 实例对象
     */
    @Override
    public MovieUser changePassword(String account, String verifyCode, String newPassword, String confirmPassword) {
        if (StringUtils.isEmpty(verifyCode)) {
            logger.info("用户" + account + ":" + ErrorMessages.VERIFICATION_NULL);
            return null;
        }
        String key = "verificationCode:" + account;
        String verifyCodeInRedis = redisUtil.get(key);
        if (StringUtils.isEmpty(verifyCodeInRedis)) {
            logger.info("用户" + account + ":Redis中无此验证码");
            return null;
        }
        if (!verifyCode.equals(verifyCodeInRedis)) {
            logger.info("用户" + account + ":" + ErrorMessages.VERIFICATION_WRONG);
            return null;
        }
        if (!newPassword.equals(confirmPassword)) {
            logger.info("用户" + account + ":两次密码输入不一致");
            return null;
        }
        String encryptionPassword = EncryptionUtil.sha384HashWithSalt(newPassword);
        int row = movieUserDao.changePassword(account, encryptionPassword);
        if (row == 0) {
            return null;
        }
        MovieUser movieUser = new MovieUser();
        movieUser.setAccount(account);
        List<MovieUser> movieUsers = movieUserDao.queryAll(movieUser);
        if (CollectionUtils.isEmpty(movieUsers)) {
            return null;
        }
        return movieUsers.get(0);
    }

    /**
     * 用户注册
     *
     * @param movieUser 要注册的用户信息
     * @return 注册好的用户信息
     */
    @Override
    public MovieUser register(MovieUser movieUser) {
        // 设置账号和密码
        movieUser.setAccount(movieUser.getPhone());
        movieUser.setPassword(EncryptionUtil.sha384HashWithSalt(movieUser.getPassword()));
        // 设置默认用户唯一名
        if (StringUtils.isEmpty(movieUser.getUserUniqueName())) {
            IdWorkerUtil idWorkerUtil = new IdWorkerUtil();
            movieUser.setUserUniqueName(String.valueOf(idWorkerUtil.nextId()));
        }
        // 根据生日设置年龄
        if (StringUtils.isNotEmpty(movieUser.getBirth())) {
            movieUser.setAge(DateFormatUtil.getAgeByBirth(movieUser.getBirth()));
        }
        // 写入
        int row = movieUserDao.insert(movieUser);
        if (row == 0) {
            return null;
        }
        logger.info("注册用户：" + movieUser.toString());
        return movieUser;
    }

    /**
     * 通过user_unique_name查询用户信息
     *
     * @param userUniqueName 用户唯一名
     * @return 单条数据
     */
    @Override
    public MovieUser queryByUserUniqueName(String userUniqueName) {
        return this.movieUserDao.queryByUserUniqueName(userUniqueName);
    }


}