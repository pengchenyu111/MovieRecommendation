package com.pcy.movierecommendation.service;

/**
 * 获取验证码的接口
 *
 * @author PengChenyu
 * @since 2020-12-20 00:18:19
 */
public interface VerificationCodeService {

    /**
     * 通过AliyunMessageUtil工具类生成验证码
     *
     * @param phoneNumber 用户手机号
     * @return 是否成功
     */
    boolean getCode(String phoneNumber);
}
