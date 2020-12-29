package com.pcy.movierecommendation.entity.movieReviews;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (MovieReviews)实体类
 *
 * @author PengChenyu
 * @since 2020-12-29 20:08:18
 */
@ApiModel(value = "MovieReviews", description = "用户评论类")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieReviews implements Serializable {

    private static final long serialVersionUID = -60131253465250408L;

    @ApiModelProperty("评论id")
    private String reviewId;

    @ApiModelProperty("电影豆瓣id")
    private Integer doubanId;

    @ApiModelProperty("用户唯一名字标志，短评上没有id，以此做唯一标识")
    private String userUniqueName;

    @ApiModelProperty("用户头像url")
    private String userHeadPortraitUrl;

    @ApiModelProperty("用户主页链接")
    private String userUrl;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("用户对电影的评分星级，5星级")
    private Integer userMovieRating;

    @ApiModelProperty("用户对电影的评分时间")
    private String userMovieRatingTime;

    @ApiModelProperty("其他用户对此评论的赞同数")
    private Integer userMovieRatingAgree;

    @ApiModelProperty("评论内容")
    private String userMovieRatingContent;

    @ApiModelProperty("电影评论好评率")
    private String moviePositiveRate;

    @ApiModelProperty("电影评论一般评率")
    private String movieGeneralRate;

    @ApiModelProperty("电影评论差评率")
    private String movieNegativeRate;

}