package com.pcy.movierecommendation.entity.imdbRatings;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import scala.Serializable;

import java.util.List;

/**
 * 格式化的IMDb电影评分对象
 *
 * @author PengChenyu
 * @since 2020-12-28 22:20:25
 */
@ApiModel(value = "FormatImagRatings", description = "格式化的IMDb电影评分对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormatImagRatings implements Serializable {

    private static final long serialVersionUID = -6005903637824391070L;

    @ApiModelProperty("IMDb id")
    private String imdbId;

    @ApiModelProperty("豆瓣id")
    private Integer doubanId;

    @ApiModelProperty("IMDb评分")
    private Double imdbRating;

    @ApiModelProperty("各级评分、权重、投票数，从10到1")
    private AllRangeRatingScoreWeightVote allRangeRatingScoreWeightVote;

    @ApiModelProperty("各年龄段评分、投票数，all|<18|18-29|30-44|>45")
    private AllAgeRatingVote allAgeRatingVote;

    @ApiModelProperty("各性别评分情况，男性|女性")
    private AllSexRatingVote allSexRatingVote;


}
