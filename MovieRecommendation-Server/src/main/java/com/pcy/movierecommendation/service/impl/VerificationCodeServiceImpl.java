package com.pcy.movierecommendation.service.impl;

import com.pcy.movierecommendation.core.utils.AliyunMessageUtil;
import com.pcy.movierecommendation.core.utils.RedisUtil;
import com.pcy.movierecommendation.service.VerificationCodeService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author PengChenyu
 * @since 2020-12-20 00:20:34
 */
@Service("verificationCodeService")
public class VerificationCodeServiceImpl implements VerificationCodeService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * redis内部短信设置
     */
    private static final int EXPIRE = 300;
    private static final int DEFAULT_DB = 0;

    @Resource
    RedisUtil redisUtil;

    /**
     * 通过AliyunMessageUtil工具类生成验证码
     * 及时存入redis
     *
     * @param phoneNumber 用户手机号
     * @return 是否成功
     */
    @Override
    public boolean getCode(String phoneNumber) {
        String sixNum = AliyunMessageUtil.sendVerificationCode(phoneNumber);
        logger.info("向" + phoneNumber + "用户发送验证码：" + sixNum);
        if (StringUtils.isEmpty(sixNum)) {
            return false;
        }
        return writeIntoRedis(phoneNumber, sixNum);
    }

    /**
     * 将验证码写入Redis
     *
     * @param phoneNumber 电话号码
     * @param sixNum      验证码
     */
    private boolean writeIntoRedis(String phoneNumber, String sixNum) {
        String key = "verificationCode:" + phoneNumber;
        redisUtil.set(key, sixNum, DEFAULT_DB);
        redisUtil.expire(key, EXPIRE, DEFAULT_DB);
        logger.info("写入Redis，verificationCode:" + sixNum);
        return redisUtil.exists(key);
    }


    /**
     * 检查验证码是否正确
     *
     * @param phoneNumber 手机号
     * @param code        用户填写的验证码
     * @return 是否成功
     */
    @Override
    public boolean checkCode(String phoneNumber, String code) {
        if (StringUtils.isEmpty(code)) {
            return false;
        }
        // 检查Redis中的验证码
        String key = "verificationCode:" + phoneNumber;
        String codeInRedis = redisUtil.get(key);
        if (StringUtils.isEmpty(codeInRedis)) {
            return false;
        }
        return code.equals(codeInRedis);
    }
}
