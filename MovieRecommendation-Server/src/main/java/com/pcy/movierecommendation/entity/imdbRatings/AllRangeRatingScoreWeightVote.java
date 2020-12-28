package com.pcy.movierecommendation.entity.imdbRatings;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author PengChenyu
 * @since 2020-12-28 22:49:40
 */
@ApiModel(value = "AllRangeRatingScoreWeightVote", description = "各级评分、权重、投票数，从10到1")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllRangeRatingScoreWeightVote implements Serializable {

    private static final long serialVersionUID = 8149459380576183891L;

    @ApiModelProperty("10级评分、权重、投票数")
    private RatingScoreWeightVote ratingScoreWeightVote10;

    @ApiModelProperty("9级评分、权重、投票数")
    private RatingScoreWeightVote ratingScoreWeightVote9;

    @ApiModelProperty("8级评分、权重、投票数")
    private RatingScoreWeightVote ratingScoreWeightVote8;

    @ApiModelProperty("7级评分、权重、投票数")
    private RatingScoreWeightVote ratingScoreWeightVote7;

    @ApiModelProperty("6级评分、权重、投票数")
    private RatingScoreWeightVote ratingScoreWeightVote6;

    @ApiModelProperty("5级评分、权重、投票数")
    private RatingScoreWeightVote ratingScoreWeightVote5;

    @ApiModelProperty("4级评分、权重、投票数")
    private RatingScoreWeightVote ratingScoreWeightVote4;

    @ApiModelProperty("3级评分、权重、投票数")
    private RatingScoreWeightVote ratingScoreWeightVote3;

    @ApiModelProperty("2级评分、权重、投票数")
    private RatingScoreWeightVote ratingScoreWeightVote2;

    @ApiModelProperty("1级评分、权重、投票数")
    private RatingScoreWeightVote ratingScoreWeightVote1;
}
