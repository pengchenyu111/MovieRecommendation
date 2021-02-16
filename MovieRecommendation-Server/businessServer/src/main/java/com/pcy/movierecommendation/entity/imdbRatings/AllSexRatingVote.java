package com.pcy.movierecommendation.entity.imdbRatings;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author PengChenyu
 * @since 2020-12-28 22:57:36
 */
@ApiModel(value = "AllSexRatingVote", description = "各性别评分情况，男性|女性")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllSexRatingVote implements Serializable {

    private static final long serialVersionUID = -2971402114405501087L;

    @ApiModelProperty("男性评分情况")
    private RatingVote maleRatings;

    @ApiModelProperty("女性评分情况")
    private RatingVote femaleRatings;
}
