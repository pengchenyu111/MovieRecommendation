<template>
  <div style="width: 100%; height: 100%; background: #545454">

    <div class="input-container">
      shurukuang
    </div>

    <div class="review-items-list-container" v-if="isShowReviewList">
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
      reviewList: [],
      isShowReviewList: true,
      isHidePagination: false,
      currentPage: 1,
      total: 0
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
            if(res.data.data.total <= pageSize) {
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
  }
  ,
  created() {
    this.searchReviews(0, 10)
  }
}
</script>

<style scoped>
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
