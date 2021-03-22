<template>
  <div>
    <div class="top-background" :style="bannerUrl">
      <div>
        <img src="../../assets/img/login/login_logo.svg" class="icon-style">
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
    <div>22222222222222222</div>
    <div>333333333333333333</div>
    <div>333333333333333333</div>
    <div>333333333333333333</div>
    <div>333333333333333333</div>
    <div>333333333333333333</div>
    <div>333333333333333333</div>
    <div>333333333333333333</div>
    <div>333333333333333333</div>
    <div>333333333333333333</div>
    <div>333333333333333333</div>
    <div>333333333333333333</div>
    <div>333333333333333333</div>
    <div>333333333333333333</div>
    <div>333333333333333333</div>
    <movie-tag :tag-list="tagList" :dialog-visible="dialogVisible"/>
  </div>
</template>

<script>
import * as userTagPreferApi from "@/api/tag/tagPreferApi";
import * as tagApi from "@/api/tag/tagApi";
import MovieTag from "@/components/tag/MovieTag";
import ImgConstants from "@/common/constant/ImgConstants";


export default {
  name: "Home",
  components: {MovieTag},
  data() {
    return {
      bannerUrl: {backgroundImage: 'url(' + ImgConstants.HOME_BANNER_URL + ')'},
      userInfo: JSON.parse(sessionStorage.getItem("currentUser")),
      dialogVisible: false,
      tagList: [],
      drawer: true,
      mini: true
    }
  },
  created() {


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
  height: 100vh;
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

</style>
