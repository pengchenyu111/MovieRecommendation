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

    /**
     * 检查验证码是否正确
     *
     * @param phoneNumber 手机号
     * @param code        用户填写的验证码
     * @return 是否成功
     */
    boolean checkCode(String phoneNumber, String code);
}
