<template>
  <div>
    <!--头部导航栏-->
    <div class="movie-detail-top-bar-container">
      <el-row>
        <el-col :span="3">
          <img src="../../../assets/img/login/login_logo.svg" class="icon-style" @click="backToHomePage">
          <span class="title-style" @click="backToHomePage">MOVIE ME</span>
        </el-col>
        <el-col :span="20">
          <el-menu default-active="1"
                   mode="horizontal"
                   @select="handleSelect"
                   class="nav-style"
                   background-color="#262626"
                   text-color="#FFFFFF"
                   active-text-color="#ec0607">
            <el-menu-item index="1">
              <router-link :to="`/movie/${$route.params.doubanId}/info`">详情</router-link>
            </el-menu-item>
            <el-menu-item index="2">
              <router-link :to="`/movie/${$route.params.doubanId}/statistic`">统计</router-link>
            </el-menu-item>
            <el-menu-item index="3">
              <router-link :to="`/movie/${$route.params.doubanId}/gallery`">剧照</router-link>
            </el-menu-item>
            <el-menu-item index="4">
              <router-link :to="`/movie/${$route.params.doubanId}/review`">评论</router-link>
            </el-menu-item>
            <el-menu-item index="5">
              <router-link :to="`/movie/${$route.params.doubanId}/single_recommend`">推荐</router-link>
            </el-menu-item>
          </el-menu>

        </el-col>
        <el-col :span="1">
          <user-avatar class="avatar-style"></user-avatar>
        </el-col>
      </el-row>
    </div>
    <!--展示-->
    <div>
      <router-view :movie-detail="movieDetail"></router-view>
    </div>
  </div>
</template>

<script>
import * as movieDetailApi from '@/api/movie/movieDetailApi'
import UserAvatar from "@/components/user/UserAvatar";

export default {
  name: "MovieHome",
  components: {UserAvatar},
  data() {
    return {
      movieDetail: {},
    }
  },
  methods: {
    backToHomePage() {
      this.$router.push('/home')
    },
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    }

  },
  created() {
    movieDetailApi.searchByDoubanId(this.$route.params.doubanId)
      .then(res => {
        this.movieDetail = res.data.data
      }).catch(() => {
      this.$message.error("请求超时！")
    })
  }
}
</script>

<style scoped>
.movie-detail-top-bar-container {
  height: 60px;
  background: #262626;
}

.icon-style {
  width: 30px;
  height: 30px;
  margin-top: 10px;
  margin-left: 10px;
  cursor: pointer;
}

.avatar-style {
  margin-top: 10px;
  margin-right: 20px;
  float: right;
}

.title-style {
  margin-left: 10px;
  color: #FFFFFF;
  font-size: 18px;
  font-family: Fantasy;
  font-style: italic;
  cursor: pointer;
}

.nav-style {
  margin-right: 50px;
  float: right;
}

</style>
