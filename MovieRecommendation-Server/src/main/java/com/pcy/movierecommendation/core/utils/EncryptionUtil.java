package com.pcy.movierecommendation.core.utils;

import org.apache.shiro.crypto.hash.Sha384Hash;

/**
 * 密码加密工具类
 *
 * @author PengChenyu
 * @since 2020-12-19 15:17:00
 */
public class EncryptionUtil {

    /**
     * 默认盐值
     */
    private static String SALT = "movierecommendation";


    /**
     * sha384HashWithSalt 加盐加密
     *
     * @param source 加密字符串
     * @return 加密后的字符串
     */
    public static String sha384HashWithSalt(String source) {
        return new Sha384Hash(source, EncryptionUtil.SALT).toString();
    }

    public static void main(String[] args) {
        System.out.println(EncryptionUtil.sha384HashWithSalt("123456"));
    }


}