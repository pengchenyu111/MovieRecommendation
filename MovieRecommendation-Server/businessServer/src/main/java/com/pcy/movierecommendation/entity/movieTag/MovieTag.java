package com.pcy.movierecommendation.entity.movieTag;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (MovieTag)实体类
 *
 * @author PengChenyu
 * @since 2021-02-01 14:34:50
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "MovieTag", description = "电影标签对象")
public class MovieTag implements Serializable {

    private static final long serialVersionUID = 280312215758617271L;

    @ApiModelProperty(value = "标签id", required = true)
    private Integer tagId;

    @ApiModelProperty(value = "标签名")
    private String tagName;


}