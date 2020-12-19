package com.pcy.movierecommendation.core.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

/**
 * 阿里云的短信服务
 *
 * @author PengChenyu
 * @since 2020-12-19 14:50:00
 */
public class AliyunMessageUtil {

    /**
     * 短信接口设置
     */
    private static final String REGION_ID = "cn-hangzhou";
    private static final String ACCESS_KET_ID = "LTAI4FyYCdUJwMWU2toLXVmi";
    private static final String SECRET = "MPQbtYUbHcTlxN1G07WbRvUBaxko7h";
    private static final String PRODUCT = "Dysmsapi";
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";
    private static final String VERSION = "2017-05-25";
    private static final String ACTION = "SendSms";
    private static final String SIGN_NAME = "智慧黄山";
    private static final String TEMPLATE_CODE = "SMS_207520927";
    private static final String SUCCESS_CODE = "OK";


    /**
     * 发送短信验证码
     *
     * @param phoneNumber 用户手机号
     * @return 6位验证码
     */
    public static String sendVerificationCode(String phoneNumber) {
        AliyunMessageUtil util = new AliyunMessageUtil();
        String sixNum = util.generateVerifyCode();
        CommonResponse response = util.sendMessage(phoneNumber, sixNum);
        if (!response.getData().contains(SUCCESS_CODE)) {
            return "";
        }
        return sixNum;
    }

    /**
     * 生成验证码
     *
     * @return 六位验证码
     */
    private String generateVerifyCode() {
        return String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
    }


    /**
     * 通过Aliyun的Api向用户发送短信
     *
     * @param phoneNumber 电话号码
     * @param sixNum      验证码
     */
    private CommonResponse sendMessage(String phoneNumber, String sixNum) {
        DefaultProfile profile = DefaultProfile.getProfile(REGION_ID, ACCESS_KET_ID, SECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(DOMAIN);
        request.setSysVersion(VERSION);
        request.setSysAction(ACTION);
        request.putQueryParameter("RegionId", REGION_ID);
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", SIGN_NAME);
        request.putQueryParameter("TemplateCode", TEMPLATE_CODE);
        request.putQueryParameter("TemplateParam", "{code:" + sixNum + "}");
        CommonResponse response = null;
        try {
            //向阿里云发送短信请求
            response = client.getCommonResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return response;
    }

}
