package com.pcy.movierecommendation.entity.imdbRatings;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 评分和投票数封装对象
 *
 * @author PengChenyu
 * @since 2020-12-28 22:28:08
 */
@ApiModel(value = "RatingVotes", description = "评分和投票数")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingVote implements Serializable {

    private static final long serialVersionUID = 5810212105504542531L;

    @ApiModelProperty("评分")
    private Double scores;

    @ApiModelProperty("投票数")
    private Integer vote;
}
