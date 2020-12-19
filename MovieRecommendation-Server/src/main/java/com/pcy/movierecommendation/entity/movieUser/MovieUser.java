package com.pcy.movierecommendation.entity.movieUser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (MovieUser)实体类
 *
 * @author PengChenyu
 * @since 2020-12-18 17:42:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "MovieUser", description = "用户对象")
public class MovieUser implements Serializable {

    private static final long serialVersionUID = 636136077410750499L;

    @ApiModelProperty(value = "用户id", required = true)
    private Integer userId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "用户唯一名字标志，短评上没有id，以此做唯一标识")
    private String userUniqueName;

    @ApiModelProperty(value = "用户头像url")
    private String userHeadPortraitUrl;

    @ApiModelProperty(value = "用户豆瓣主页链接")
    private String userUrl;

    @ApiModelProperty(value = "用户账户")
    private String account;

    @ApiModelProperty(value = "账号密码，加密")
    private String password;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "用户联系电话")
    private String phone;

    @ApiModelProperty(value = "用户性别", example = "男")
    private String sex;

    @ApiModelProperty(value = "用户生日")
    private Date birth;

    @ApiModelProperty(value = "用户年龄")
    private Integer age;

    @ApiModelProperty(value = "用户职业")
    private String profession;


}