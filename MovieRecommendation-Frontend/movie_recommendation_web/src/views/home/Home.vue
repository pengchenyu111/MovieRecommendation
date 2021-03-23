<template>
  <div>
    <div class="top-background" :style="bannerUrl">
      <div>
        <img src="../../assets/img/login/login_logo.svg" class="icon-style">
        <user-avatar class="avatar-style"/>
      </div>
      <div class="home-main-title">
        MOVIE ME
      </div>
      <div class="home-main-text">
        这是一个影像的时代，视听的时代，机械复制的时代，灵光消逝的时代
      </div>
      <div class="btn-container">
        <v-btn tile text rounded color="#FFFFFF" class="top-btn-style">全部电影</v-btn>
        <v-btn tile text rounded color="#FFFFFF" class="top-btn-style">全部榜单</v-btn>
      </div>
    </div>
    <rank-board :movie-list="alsUserRecMovieList" get-more-url="rank/als_user" class="rank-board">
      <div slot="rank-title">专属推荐</div>
    </rank-board>
    <rank-board :movie-list="recentTopMovieList" get-more-url="rank/rencent" class="rank-board">
      <div slot="rank-title">近期热门</div>
    </rank-board>
    <rank-board :movie-list="historyTopMovieList" get-more-url="rank/history" class="rank-board">
      <div slot="rank-title">历史榜单</div>
    </rank-board>
    <movie-tag :tag-list="tagList" :dialog-visible="dialogVisible"/>
  </div>
</template>

<script>
import * as userTagPreferApi from "@/api/tag/tagPreferApi";
import * as tagApi from "@/api/tag/tagApi";
import * as recommenderApi from "@/api/recommend/recommenderApi";
import MovieTag from "@/components/tag/MovieTag";
import ImgConstants from "@/common/constant/ImgConstants";
import UserAvatar from "@/components/user/UserAvatar";
import RankBoard from "@/components/rank/RankBoard";


export default {
  name: "Home",
  components: {RankBoard, UserAvatar, MovieTag},
  data() {
    return {
      bannerUrl: {backgroundImage: 'url(' + ImgConstants.HOME_BANNER_URL + ')'},
      userInfo: JSON.parse(sessionStorage.getItem("currentUser")),
      dialogVisible: false,
      tagList: [],
      recentTopMovieList: [],
      alsUserRecMovieList: [],
      historyTopMovieList: [],
    }
  },
  created() {
    // 加载电影近期热门Top20
    recommenderApi.queryRecentlyTop20()
      .then(res => {
        this.recentTopMovieList = res.data.data
      }).catch(err => {
      console.log(err);
    })
    // 加载基于ALS的用户电影推荐列表
    recommenderApi.queryALSUserRecs(this.userInfo.userId)
      .then(res => {
        this.alsUserRecMovieList = res.data.data
      }).catch(err => {
      console.log(err);
    })
    // 加载历史榜单电影列表
    recommenderApi.queryHistoryTop20()
      .then(res => {
        this.historyTopMovieList = res.data.data
      }).catch(err => {
      console.log(err);
    })


    //以下两个方法是为了加载用户喜好的电影分类
    userTagPreferApi.queryUserTagPreferById(this.userInfo.userId)
      .then(res => {
        if (!res.data.success) {
          // 如果用户没有喜好标签数据，则打开对话框让用户选择
          this.dialogVisible = true;
        }
      }).catch(err => {
      console.log(err);
    })
    tagApi.queryAllTags()
      .then(res => {
        this.tagList = res.data.data
      }).catch(err => {
      console.log(err);
    })
  },
  methods: {}
}
</script>

<style scoped>
.top-background {
  width: 100vw;
  height: 85vh;
  position: relative;
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
}

.icon-style {
  margin-top: 50px;
  margin-left: 50px;
  width: 40px;
  height: 40px;
}

.home-main-title {
  font-size: 130px;
  color: white;
  text-align: center;
  font-family: Bahnschrift, cursive;
  margin-top: 60px;
}

.home-main-text {
  font-size: 20px;
  color: white;
  text-align: center;
  font-family: "Microsoft YaHei UI", cursive;
  margin-top: 10px;
}

.btn-container {
  text-align: center;
  margin-top: 30px;
}

.top-btn-style {
  margin: 10px;
  width: 150px;
  padding-left: unset;
  padding-right: unset;
  border: solid 2px #FFFFFF;
}

.top-btn-style:hover {
  border: solid 2px #000000;
  background: #848484;
}

.avatar-style {
  margin-top: 50px;
  margin-right: 80px;
  float: right;
}

.rank-board {
  margin-left: 50px;
  margin-right: 50px;
}

</style>
