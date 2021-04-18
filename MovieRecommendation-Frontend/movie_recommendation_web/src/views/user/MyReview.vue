<template>
  <div>
    <link rel="stylesheet" type="text/css" href="https://ianlunn.github.io/Hover/css/hover.css">
    <top-bar></top-bar>
    <div class="no-review-style" v-if="!isShowReviewList">暂无评论</div>
    <div class="content-container">
      <div style="width: 100%; height: 150px; margin-top: 20px"
           v-for="(item, index) in reviewList"
           :key="index">
        <el-row>
          <!--封面-->
          <el-col :span="3" style="text-align: center">
            <el-image :src="item.coverUrl" style="height: 150px; width: 100px; cursor: pointer" class="hvr-grow-shadow"
                      @click="toMovieDetailPage(item.doubanId)">
              <div slot="error" style="text-align: center; line-height: 150px">
                <i class="el-icon-picture-outline"></i>
              </div>
            </el-image>
          </el-col>
          <!--内容-->
          <el-col :span="21">
            <div @click="toMovieDetailPage(item.doubanId)" class="title-style">{{ item.title }}</div>
            <div>{{item.userMovieRatingContent}}</div>
            <div style="margin-top: 10px">
              <img src="http://81.70.252.155:8000/movierecommendation/icon/agree_active.png">
              <span style="margin-left: 3px; color: orangered">{{ item.userMovieRatingAgree }}</span>
              <span style="margin-left: 30px; color: #f81b45">个人评分：{{ item.userMovieRating / 10.0 }}</span>
            </div>
            <div style="position: absolute; bottom: 0">
              <div style="font-style: italic; color: #666666">{{item.userMovieRatingTime}}</div>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
    <!--分页导航栏-->
    <div style="text-align: center; margin-top: 50px; padding-bottom: 100px">
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
import TopBar from "@/components/topbar/TopBar";
import * as reviewApi from "@/api/rating/reviewApi"

export default {
  name: "MyReview",
  components: {TopBar},
  data() {
    return {
      isHidePagination: false,
      isShowReviewList: true,
      reviewList: [],
      currentPage: 1,
      total: 0,
    }
  },
  computed: {
    getCover() {
      return (doubanId) => {
        return `http://81.70.252.155:8000/movierecommendation/movie/poster/${doubanId}.jpg`
      }
    }
  },
  methods: {
    searchReviews(pageNum, pageSize) {
      let id = this.$route.params.userId
      reviewApi.queryUserReviews(id, pageNum, pageSize)
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
    toMovieDetailPage(doubanId) {
      let href = this.$router.resolve({path: `/movie/${doubanId}`});
      window.open(href.href, '_blank')
    }
  },
  created() {
    this.searchReviews(0, 10)
  }
}
</script>

<style scoped>
.content-container {
  margin-top: 10vh;
  margin-left: 2vw;
  margin-right: 2vw;
}

.no-review-style {
  margin-top: 20vh;
  text-align: center;
  font-size: 30px;
  font-weight: bold;
  color: black;
}

.title-style {
  font-size: 16px;
  font-weight: bold;
  color: black;
  margin-bottom: 10px;
  cursor: pointer;
}

.title-style:hover {
  color: dodgerblue;
  margin-bottom: 10px;
}

</style>
