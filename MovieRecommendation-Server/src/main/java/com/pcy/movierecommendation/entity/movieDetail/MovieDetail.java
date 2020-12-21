package com.pcy.movierecommendation.entity.movieDetail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (MovieDetail)实体类
 *
 * @author PengChenyu
 * @since 2020-12-21 21:41:50
 */
@ApiModel(value = "MovieDetail", description = "电影详情对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDetail implements Serializable {

    private static final long serialVersionUID = -42725455283108651L;


    @ApiModelProperty("豆瓣id")
    private Integer doubanId;

    @ApiModelProperty("电影名")
    private String title;

    @ApiModelProperty("电影简介")
    private String briefInstruction;

    @ApiModelProperty("导演列表,/分割，注意两边有空格")
    private String directors;

    @ApiModelProperty("编剧列表,/分割，注意两边有空格")
    private String screenwriters;

    @ApiModelProperty("演员列表,/分割，注意两边有空格")
    private String casts;

    @ApiModelProperty("类型列表,/分割，注意两边有空格")
    private String types;

    @ApiModelProperty("制片国家/地区")
    private String productionCountryArea;

    @ApiModelProperty("语言")
    private String language;

    @ApiModelProperty("上映日期列表,/分割，注意两边有空格")
    private String publishDate;

    @ApiModelProperty("片长")
    private String runtime;

    @ApiModelProperty("评分分数，10分制")
    private Double ratingScore;

    @ApiModelProperty("评分星级，5星制")
    private Integer ratingStar;

    @ApiModelProperty("评分人数")
    private Integer ratingNum;

    @ApiModelProperty("评5星占比")
    private String rating5StarWeight;

    @ApiModelProperty("评4星占比")
    private String rating4StarWeight;

    @ApiModelProperty("评3星占比")
    private String rating3StarWeight;

    @ApiModelProperty("评2星占比")
    private String rating2StarWeight;

    @ApiModelProperty("评1星占比")
    private String rating1StarWeight;

    @ApiModelProperty("好于其他类型影片占比，列表")
    private String betterThan;

    @ApiModelProperty("豆瓣电影链接")
    private String doubanUrl;

    @ApiModelProperty("电影海报链接")
    private String coverUrl;

    @ApiModelProperty("IMDb链接")
    private String imdbUrl;

    @ApiModelProperty("电影图片列表，逗号分割")
    private String imgList;


}