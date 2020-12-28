package com.pcy.movierecommendation.entity.imdbRatings;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author PengChenyu
 * @since 2020-12-28 22:54:56
 */
@ApiModel(value = "AllAgeRatingVote", description = "各年龄段评分、投票数，all|<18|18-29|30-44|>45")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllAgeRatingVote implements Serializable {

    private static final long serialVersionUID = -5642579758237761900L;

    @ApiModelProperty("全年龄段评分情况")
    private RatingVote ageAll;

    @ApiModelProperty("年龄小于18的评分情况")
    private RatingVote ageLessThan18;

    @ApiModelProperty("年龄18-29的评分情况")
    private RatingVote age1829;

    @ApiModelProperty("年龄30-44的评分情况")
    private RatingVote age3044;

    @ApiModelProperty("年龄大于45的评分情况")
    private RatingVote ageMoreThan45;
}
