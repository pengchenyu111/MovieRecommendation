<template>
  <div style="width: 100%; height: 100%; background: #545454">

    <div class="input-container">
      <div style="color: #FFFFFF">写下您的短评:</div>
      <el-input
        style="padding-top: 20px"
        type="textarea"
        :autosize="{ minRows: 4, maxRows: 6 }"
        placeholder="请输入内容"
        maxlength="300"
        show-word-limit
        :clearable="true"
        v-model="myContent">
      </el-input>
      <div style="margin-top: 20px">
        <span style="color: #FFFFFF">评分：</span>
        <span style="display: inline-block">
          <el-rate
            v-model="myRating"
            text-color="#ff9900"
            :colors="colors"
            show-text>
          </el-rate>
        </span>
        <span style="float: right">
          <el-button type="primary" round @click="submitReview">发表评论</el-button>
        </span>
      </div>
      <div style="padding-top: 20px">
        <el-divider></el-divider>
      </div>
    </div>

    <div class="review-items-list-container" v-if="isShowReviewList">
      <div class="item-container" v-if="isShowMyReview">
        <review-item :movie-review="myReview"></review-item>
      </div>
      <div class="item-container"
           v-for="(item, index) in reviewList"
           :key="index">
        <review-item :movie-review="item"></review-item>
      </div>
    </div>
    <div class="no-review-container" v-if="!isShowReviewList">
      暂无评论
    </div>
    <!--分页导航栏-->
    <div style="text-align: center; margin-top: 50px">
      <el-pagination
        :hide-on-single-page="isHidePagination"
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-size="10"
        layout="prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>
    <!--回到顶部-->
    <el-backtop></el-backtop>
  </div>
</template>

<script>
import * as  reviewApi from '@/api/rating/reviewApi'
import ReviewItem from "@/components/rating/ReviewItem";

export default {
  name: "MovieReview",
  components: {ReviewItem},
  props: {
    movieDetail: {}
  },
  data() {
    return {
      userInfo: JSON.parse(sessionStorage.getItem("currentUser")),
      myContent: '',
      myRating: 0,
      isShowMyReview: false,
      myReview: {},

      reviewList: [],
      isShowReviewList: true,
      isHidePagination: false,
      currentPage: 1,
      total: 0,
      colors: ['#99A9BF', '#F7BA2A', '#FF9900'],
    }
  },
  methods: {
    searchReviews(pageNum, pageSize) {
      let id = this.$route.params.doubanId
      reviewApi.queryByDoubanIdPage(id, pageNum, pageSize)
        .then(res => {
          if (res.data.success) {
            this.reviewList = res.data.data.list
            this.total = res.data.data.total
            if (res.data.data.total === 0) {
              this.isShowReviewList = false
            }
            if (res.data.data.total <= pageSize) {
              this.isHidePagination = true
            }
          }
        }).catch(() => {
        this.$message.error("请求失败！")
      })
    },
    handleCurrentChange(val) {
      this.searchReviews(val, 10)
    },
    submitReview() {
      let movieReviewRequest = {
        doubanId: this.$route.params.doubanId,
        movieGeneralRate: "0%",
        movieNegativeRate: "0%",
        moviePositiveRate: "0%",
        reviewId: "",
        userMovieRating: this.myRating,
        userMovieRatingAgree: 0,
        userMovieRatingContent: this.myContent,
        userMovieRatingTime: "",
        userName: this.userInfo.userName,
        userUniqueName: this.userInfo.userUniqueName,
        userUrl: this.userInfo.userUrl,
        userHeadPortraitUrl: this.userInfo.userHeadPortraitUrl,
      }
      reviewApi.review(movieReviewRequest)
      .then(res => {
        if (res.data.success) {
          this.$message.success("评论成功！")
          this.isShowMyReview = true
          this.myReview = res.data.data
        }else {
          this.$message.error("评论失败！")
        }
      })
      .catch(() => {
        this.$message.error("请求超时！")
      })
    }
  }
  ,
  created() {
    this.searchReviews(0, 10)
  }
}
</script>

<style scoped>
@import url("//unpkg.com/element-ui@2.15.1/lib/theme-chalk/index.css");

.input-container {
  padding-top: 2vh;
  margin-left: 2vw;
  margin-right: 2vw;
}

.review-items-list-container {
  margin-left: 2vw;
  margin-right: 2vw;
  margin-top: 50px;
}

.no-review-container {
  text-align: center;
  color: #FFFFFF;
  font-size: 30px;
  line-height: 100vh;
}
</style>
