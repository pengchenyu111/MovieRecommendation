package com.pcy.movierecommendation.core.constants;

/**
 * 错误码
 *
 * @author PengChenyu
 * @since 2020-12-18 17:42:00
 */
public class ErrorMessages {

    /**
     * 请求
     */
    public static final String REQUEST_SUCCESS = "请求成功";
    public static final String REQUEST_FAIL = "请求失败";

    /**
     * 查询
     */
    public static final String QUERY_NULL = "查询结果为空";

    /**
     * 登录
     */
    public static final String LOGIN_ACCOUNT_PASSWORD_WRONG = "账号或密码错误";
    public static final String LOGIN_SUCCESS = "登录成功";

    /**
     * 注册
     */
    public static final String REGISTER_SUCCESS = "注册成功";
    public static final String REGISTER_FAIL = "注册失败";
    public static final String REGISTER_ALREADY = "账户已注册";

    /**
     * 验证码
     */
    public static final String VERIFICATION_SUCCESS = "获取验证码成功";
    public static final String VERIFICATION_FAIL = "获取验证码失败，请稍后再试";
    public static final String VERIFICATION_WRONG = "验证码错误";
    public static final String VERIFICATION_NULL = "验证码为空";
    public static final String VERIFICATION_CORRECT = "验证码正确";

    /**
     * 修改信息
     */
    public static final String CHANGE_FAIL = "修改失败";
    public static final String CHANGE_SUCCESS = "修改成功";

    /**
     * ElasticSearch
     */
    public static final String ELASTICSEARCH_SEARCH_SUCCESS = "查询成功";
    public static final String ELASTICSEARCH_INDEX_NULL = "索引不存在";
    public static final String ELASTICSEARCH_INDEX_CREATE_SUCCESS = "索引创建成功";
    public static final String ELASTICSEARCH_INDEX_CREATE_FAIL = "索引创建失败";
    public static final String ELASTICSEARCH_INDEX_DELETE_SUCCESS = "索引删除成功";
    public static final String ELASTICSEARCH_INDEX_DELETE_FAIL = "索引删除失败";


}
