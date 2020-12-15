/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : movie_recommendation

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 15/12/2020 11:17:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for imdb_ratings
-- ----------------------------
DROP TABLE IF EXISTS `imdb_ratings`;
CREATE TABLE `imdb_ratings`  (
  `imdb_id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'IMDb id',
  `douban_id` int(11) NOT NULL COMMENT '豆瓣id',
  `imdb_rating` double(5, 1) NULL DEFAULT NULL COMMENT 'IMDb评分',
  `rating_scores` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '各级评分，1-10，|分割',
  `rating_scores_weights` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '各级评分权重，|分割',
  `rating_scores_votes` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '各级评分投票数，|分割',
  `age_all` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '全年龄段评分情况，评分|投票数',
  `age_less_than_18` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '年龄小于18的评分情况，评分|投票数',
  `age_18_29` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '年龄18-29的评分情况，评分|投票数',
  `age_30_44` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '年龄30-44的评分情况，评分|投票数',
  `age_more_than_45` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '年龄大于45的评分情况，评分|投票数',
  `male_ratings` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '男性评分情况',
  `female_ratings` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '女性评分情况',
  PRIMARY KEY (`imdb_id`, `douban_id`) USING BTREE,
  UNIQUE INDEX `index_imdb_id`(`imdb_id`) USING BTREE,
  UNIQUE INDEX `index_douban_id`(`douban_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for movie_brief_introduction
-- ----------------------------
DROP TABLE IF EXISTS `movie_brief_introduction`;
CREATE TABLE `movie_brief_introduction`  (
  `douban_id` int(10) NOT NULL COMMENT '豆瓣id',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电影名',
  `rate` decimal(5, 1) NULL DEFAULT NULL COMMENT '评分',
  `star` int(10) NULL DEFAULT NULL COMMENT '用户评星大致区间',
  `directors` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '导演，|分割',
  `casts` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '演员，|分割',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电影豆瓣链接',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电影海报链接',
  `cover_x` int(10) NULL DEFAULT NULL COMMENT '电影海报x长度',
  `cover_y` int(10) NULL DEFAULT NULL COMMENT '电影海报y长度',
  PRIMARY KEY (`douban_id`) USING BTREE,
  UNIQUE INDEX `index_douban_id`(`douban_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for movie_detail
-- ----------------------------
DROP TABLE IF EXISTS `movie_detail`;
CREATE TABLE `movie_detail`  (
  `douban_id` int(11) NOT NULL COMMENT '豆瓣id',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电影名',
  `brief_instruction` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '电影简介',
  `directors` varchar(511) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '导演列表,/分割，注意两边有空格',
  `screenwriters` varchar(511) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编剧列表,/分割，注意两边有空格',
  `casts` varchar(511) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '演员列表,/分割，注意两边有空格',
  `types` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型列表,/分割，注意两边有空格',
  `production_country_area` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '制片国家/地区',
  `language` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '语言',
  `publish_date` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上映日期列表,/分割，注意两边有空格',
  `runtime` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '片长',
  `rating_score` double(5, 1) NULL DEFAULT NULL COMMENT '评分分数，10分制',
  `rating_star` int(10) NULL DEFAULT NULL COMMENT '评分星级，5星制',
  `rating_num` int(10) NULL DEFAULT NULL COMMENT '评分人数',
  `rating_5_star_weight` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评5星占比',
  `rating_4_star_weight` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评4星占比',
  `rating_3_star_weight` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评3星占比',
  `rating_2_star_weight` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评2星占比',
  `rating_1_star_weight` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评1星占比',
  `better_than` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '好于其他类型影片占比，列表',
  `douban_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '豆瓣电影链接',
  `cover_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电影海报链接',
  `imdb_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IMDb链接',
  `img_list` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '电影图片列表，逗号分割',
  PRIMARY KEY (`douban_id`) USING BTREE,
  UNIQUE INDEX `index_douban_id`(`douban_id`) USING BTREE,
  FULLTEXT INDEX `index_title`(`title`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for movie_reviews
-- ----------------------------
DROP TABLE IF EXISTS `movie_reviews`;
CREATE TABLE `movie_reviews`  (
  `review_id` char(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '评论id',
  `douban_id` int(11) NULL DEFAULT NULL COMMENT '电影豆瓣id',
  `user_unique_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户唯一名字标志，短评上没有id，以此做唯一标识',
  `user_head_portrait_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户头像url',
  `user_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户主页链接',
  `user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `user_movie_rating` int(5) NULL DEFAULT NULL COMMENT '用户对电影的评分星级，5星级',
  `user_movie_rating_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户对电影的评分时间',
  `user_movie_rating_agree` int(11) NULL DEFAULT NULL COMMENT '其他用户对此评论的赞同数',
  `user_movie_rating_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '评论内容',
  `movie_positive_rate` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电影评论好评率',
  `movie_general_rate` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电影评论一般评率',
  `movie_negative_rate` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电影评论差评率',
  PRIMARY KEY (`review_id`) USING BTREE,
  UNIQUE INDEX `index_review_id`(`review_id`) USING BTREE,
  INDEX `index_douban_id`(`douban_id`) USING BTREE,
  FULLTEXT INDEX `index_user_unique_name`(`user_unique_name`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for movie_user
-- ----------------------------
DROP TABLE IF EXISTS `movie_user`;
CREATE TABLE `movie_user`  (
  `user_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `user_unique_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户唯一名字标志，短评上没有id，以此做唯一标识',
  `user_head_portrait_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户头像url',
  `user_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户豆瓣主页链接',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `index_user_id`(`user_id`) USING BTREE,
  FULLTEXT INDEX `index_user_name`(`user_name`),
  FULLTEXT INDEX `index_user_unique_name`(`user_unique_name`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for movie_user_ratings
-- ----------------------------
DROP TABLE IF EXISTS `movie_user_ratings`;
CREATE TABLE `movie_user_ratings`  (
  `review_id` char(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '评论id',
  `douban_id` int(11) NULL DEFAULT NULL COMMENT '豆瓣id',
  `user_unique_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户唯一名字标志，短评上没有id，以此做唯一标识',
  `user_movie_rating` double(2, 1) NULL DEFAULT NULL COMMENT '用户评分',
  PRIMARY KEY (`review_id`) USING BTREE,
  UNIQUE INDEX `index_review_id`(`review_id`) USING BTREE,
  INDEX `index_douban_id`(`douban_id`) USING BTREE,
  FULLTEXT INDEX `index_user_unique_name`(`user_unique_name`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for subjects
-- ----------------------------
DROP TABLE IF EXISTS `subjects`;
CREATE TABLE `subjects`  (
  `douban_id` int(10) NOT NULL COMMENT '豆瓣id',
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型：movie，music，book',
  PRIMARY KEY (`douban_id`) USING BTREE,
  UNIQUE INDEX `index_douban_id`(`douban_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
