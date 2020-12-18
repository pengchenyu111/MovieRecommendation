package com.pcy.movierecommendation.entity.movieUser;

import java.io.Serializable;

/**
 * (MovieUser)实体类
 *
 * @author PengChenyu
 * @since 2020-12-18 17:42:00
 */
public class MovieUser implements Serializable {
    private static final long serialVersionUID = 636136077410750499L;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户唯一名字标志，短评上没有id，以此做唯一标识
     */
    private String userUniqueName;
    /**
     * 用户头像url
     */
    private String userHeadPortraitUrl;
    /**
     * 用户豆瓣主页链接
     */
    private String userUrl;
    /**
     * 用户账户
     */
    private String account;
    /**
     * 账号密码，加密
     */
    private String password;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 用户联系电话
     */
    private String phone;
    /**
     * 用户性别
     */
    private String sex;
    /**
     * 用户生日
     */
    private Object birth;
    /**
     * 用户年龄
     */
    private Integer age;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserUniqueName() {
        return userUniqueName;
    }

    public void setUserUniqueName(String userUniqueName) {
        this.userUniqueName = userUniqueName;
    }

    public String getUserHeadPortraitUrl() {
        return userHeadPortraitUrl;
    }

    public void setUserHeadPortraitUrl(String userHeadPortraitUrl) {
        this.userHeadPortraitUrl = userHeadPortraitUrl;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Object getBirth() {
        return birth;
    }

    public void setBirth(Object birth) {
        this.birth = birth;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}