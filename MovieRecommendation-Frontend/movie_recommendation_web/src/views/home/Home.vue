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
        <v-btn tile text rounded color="#FFFFFF" class="top-btn-style" @click="toMovieSearchPage">全部电影</v-btn>
        <v-btn tile text rounded color="#FFFFFF" class="top-btn-style" @click="toRankPage">全部榜单</v-btn>
      </div>
    </div>
    <rank-board :movie-list="alsUserRecMovieList" get-more-url="rank/als_user_top" class="rank-board">
      <div slot="rank-title">专属推荐</div>
    </rank-board>
    <rank-board :movie-list="recentTopMovieList" get-more-url="rank/rencent_top" class="rank-board">
      <div slot="rank-title">近期热门</div>
    </rank-board>
    <rank-board :movie-list="historyTopMovieList" get-more-url="rank/history_top" class="rank-board">
      <div slot="rank-title">历史榜单</div>
    </rank-board>
  </div>
</template>

<script>
import * as recommenderApi from "@/api/recommend/recommenderApi";
import ImgConstants from "@/common/constant/ImgConstants";
import UserAvatar from "@/components/user/UserAvatar";
import RankBoard from "@/components/rank/RankBoard";


export default {
  name: "Home",
  components: {RankBoard, UserAvatar},
  data() {
    return {
      bannerUrl: {backgroundImage: 'url(' + ImgConstants.HOME_BANNER_URL + ')'},
      userInfo: JSON.parse(sessionStorage.getItem("currentUser")),
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

  },
  methods: {
    toRankPage() {
      this.$router.push('/rank')
    },
    toMovieSearchPage() {
      this.$router.push('/search')
    }
  }
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
  user-select: none;
}

.home-main-text {
  font-size: 20px;
  color: white;
  text-align: center;
  font-family: "Microsoft YaHei UI", cursive;
  margin-top: 10px;
  user-select: none;
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
