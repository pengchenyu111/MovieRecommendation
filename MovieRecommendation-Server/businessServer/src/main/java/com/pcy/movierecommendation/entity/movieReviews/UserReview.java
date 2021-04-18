package com.pcy.movierecommendation.entity.movieReviews;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 单个用户的历史评论类
 *
 * @author PengChenyu
 * @since 2021-04-18 15:50:11
 */
@ApiModel(value = "UserReview", description = "单个用户的历史评论类")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReview implements Serializable {

    private static final long serialVersionUID = -8401013854969204189L;

    @ApiModelProperty("评论id")
    private String reviewId;

    @ApiModelProperty("用户唯一名字标志，短评上没有id，以此做唯一标识")
    private String userUniqueName;

    @ApiModelProperty("豆瓣id")
    private Integer doubanId;

    @ApiModelProperty("电影名")
    private String title;

    @ApiModelProperty("电影海报链接")
    private String coverUrl;

    @ApiModelProperty("评论内容")
    private String userMovieRatingContent;

    @ApiModelProperty("用户对电影的评分星级，5星级")
    private Integer userMovieRating;

    @ApiModelProperty("其他用户对此评论的赞同数")
    private Integer userMovieRatingAgree;

    @ApiModelProperty("用户对电影的评分时间")
    private String userMovieRatingTime;

}
