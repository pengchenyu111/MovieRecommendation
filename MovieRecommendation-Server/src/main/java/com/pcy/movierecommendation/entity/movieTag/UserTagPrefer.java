package com.pcy.movierecommendation.entity.movieTag;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (UserTagPrefer)实体类
 *
 * @author PengChenyu
 * @since 2021-02-03 21:32:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "UserTagPrefer", description = "用户更感兴趣电影标签对象")
public class UserTagPrefer implements Serializable {

    private static final long serialVersionUID = -23292695884942233L;

    @ApiModelProperty(value = "用户id", required = true)
    private Integer userId;

    @ApiModelProperty(value = "用户喜欢的分类列表,英文逗号,分隔")
    private String tagList;

}