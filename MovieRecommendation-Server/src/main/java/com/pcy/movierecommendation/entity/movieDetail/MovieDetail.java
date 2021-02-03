package com.pcy.movierecommendation.entity.movieDetail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

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
    @Field("douban_id")
    private Integer doubanId;

    @ApiModelProperty("电影名")
    private String title;

    @ApiModelProperty("电影简介")
    @Field("brief_instruction")
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
    @Field("production_country_area")
    private String productionCountryArea;

    @ApiModelProperty("语言")
    private String language;

    @ApiModelProperty("上映日期列表,/分割，注意两边有空格")
    @Field("publish_date")
    private String publishDate;

    @ApiModelProperty("片长")
    private String runtime;

    @ApiModelProperty("评分分数，10分制")
    @Field("rating_score")
    private Double ratingScore;

    @ApiModelProperty("评分星级，5星制")
    @Field("rating_star")
    private Integer ratingStar;

    @ApiModelProperty("评分人数")
    @Field("rating_num")
    private Integer ratingNum;

    @ApiModelProperty("评5星占比")
    @Field("rating_5_star_weight")
    private String rating5StarWeight;

    @ApiModelProperty("评4星占比")
    @Field("rating_4_star_weight")
    private String rating4StarWeight;

    @ApiModelProperty("评3星占比")
    @Field("rating_3_star_weight")
    private String rating3StarWeight;

    @ApiModelProperty("评2星占比")
    @Field("rating_2_star_weight")
    private String rating2StarWeight;

    @ApiModelProperty("评1星占比")
    @Field("rating_1_star_weight")
    private String rating1StarWeight;

    @ApiModelProperty("好于其他类型影片占比，列表")
    @Field("better_than")
    private String betterThan;

    @ApiModelProperty("豆瓣电影链接")
    @Field("douban_url")
    private String doubanUrl;

    @ApiModelProperty("电影海报链接")
    @Field("cover_url")
    private String coverUrl;

    @ApiModelProperty("IMDb链接")
    @Field("imdb_url")
    private String imdbUrl;

    @ApiModelProperty("电影图片列表，逗号分割")
    @Field("img_list")
    private String imgList;


}