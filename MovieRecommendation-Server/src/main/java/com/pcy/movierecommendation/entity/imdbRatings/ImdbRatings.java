package com.pcy.movierecommendation.entity.imdbRatings;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (ImdbRatings)实体类
 *
 * @author PengChenyu
 * @since 2020-12-27 22:28:27
 */
@ApiModel(value = "ImdbRatings", description = "IMDb电影频分对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImdbRatings implements Serializable {

    private static final long serialVersionUID = 457122324895732458L;

    @ApiModelProperty("IMDb id")
    private String imdbId;

    @ApiModelProperty("豆瓣id")
    private Integer doubanId;

    @ApiModelProperty("IMDb评分")
    private Double imdbRating;

    @ApiModelProperty("各级评分，1-10，|分割")
    private String ratingScores;

    @ApiModelProperty("各级评分权重，|分割")
    private String ratingScoresWeights;

    @ApiModelProperty("各级评分投票数，|分割")
    private String ratingScoresVotes;

    @ApiModelProperty("全年龄段评分情况，评分|投票数")
    private String ageAll;

    @ApiModelProperty("年龄小于18的评分情况，评分|投票数")
    private String ageLessThan18;

    @ApiModelProperty("年龄18-29的评分情况，评分|投票数")
    private String age1829;

    @ApiModelProperty("年龄30-44的评分情况，评分|投票数")
    private String age3044;

    @ApiModelProperty("年龄大于45的评分情况，评分|投票数")
    private String ageMoreThan45;

    @ApiModelProperty("男性评分情况")
    private String maleRatings;

    @ApiModelProperty("女性评分情况")
    private String femaleRatings;


}