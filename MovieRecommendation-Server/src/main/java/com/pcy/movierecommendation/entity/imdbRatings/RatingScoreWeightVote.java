package com.pcy.movierecommendation.entity.imdbRatings;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 10级评分分布情况
 *
 * @author PengChenyu
 * @since 2020-12-28 22:23:57
 */
@ApiModel(value = "RatingScoreWeightVote", description = "10级评分分布情况")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingScoreWeightVote implements Serializable {

    private static final long serialVersionUID = 6699575467159197448L;

    @ApiModelProperty("评分，1-10")
    private Integer ratingScores;

    @ApiModelProperty("各级评分权重，带百分号")
    private String ratingScoresWeights;

    @ApiModelProperty("各级评分投票数")
    private Integer ratingScoresVotes;
}
