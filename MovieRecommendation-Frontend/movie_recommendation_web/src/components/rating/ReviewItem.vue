<template>
  <div class="item-root-container">
    <el-row>
      <el-col :span="1">
        <div>
          <el-avatar :src="movieReview.userHeadPortraitUrl" size="medium"></el-avatar>
        </div>
      </el-col>
      <el-col :span="23">
        <div>
          <span style="color: #35eeff">{{ movieReview.userName }}</span>
          <span style="display: inline-block; margin-left: 20px;">
            <el-rate
              :value="movieReview.userMovieRating / 10.0"
              disabled
              show-score
              text-color="#ff9900"
              :colors="colors"
              score-template="{value}">
            </el-rate>
          </span>
        </div>

        <div class="item-content">
          {{ movieReview.userMovieRatingContent }}
        </div>

        <div style="margin-top: 10px">
          <span>
            <img src="http://81.70.252.155:8000/movierecommendation/icon/agree.png" v-if="!isAgreeActive" @click="agree"
                 style="cursor: pointer">
            <img src="http://81.70.252.155:8000/movierecommendation/icon/agree_active.png" v-if="isAgreeActive">
          </span>
          <span style="margin-left: 3px; color: #FFFFFF">{{ totalAgree }}</span>
          <span style="float: right; color: #9e9e9e">{{ movieReview.userMovieRatingTime }}</span>
        </div>
      </el-col>
    </el-row>

  </div>
</template>

<script>
import * as  reviewApi from '@/api/rating/reviewApi'

export default {
  name: "ReviewItem",
  props: {
    movieReview: {}
  },
  data() {
    return {
      colors: ['#99A9BF', '#F7BA2A', '#FF9900'],
      isAgreeActive: false,
      totalAgree: this.movieReview.userMovieRatingAgree
    }
  },
  methods: {
    agree() {
      reviewApi.reviewAgree(this.movieReview.reviewId)
        .then(res => {
          if (res.data.success) {
            this.totalAgree = res.data.data.userMovieRatingAgree
            this.isAgreeActive = true
          }
        }).catch(() => {
          this.$message.error("点赞失败！")
      })
    }
  }
}
</script>

<style scoped>

.item-root-container {
  margin-bottom: 30px;
}

.item-content {
  margin-top: 10px;
  color: #FFFFFF;
}
</style>
