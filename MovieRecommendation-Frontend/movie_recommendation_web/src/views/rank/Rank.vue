<template>
  <div>
    <top-bar></top-bar>
    <el-row style="height: 100vh; width: 100%; position: relative">
      <el-col :span="5">
        <div class="left_navi_container">
          <div style="margin-top: 10vh">
            <img src="../../assets/img/logo_2.png" @click="backToHome">
            <div class=left-category-title>全 部 榜 单</div>
          </div>
          <div class="left-category-item-container">
            <div class="left-category-item">
              <router-link to="/rank/history_top" class="left-category-item-hover">历史榜单</router-link>
            </div>
            <div class="left-category-item">
              <router-link to="/rank/recent_top" class="left-category-item-hover">近期热门榜单</router-link>
            </div>
            <div class="left-category-item">
              <router-link to="/rank/per_tag_top" class="left-category-item-hover">各类别榜单</router-link>
            </div>
            <div class="left-category-item">
              <router-link to="/rank/als_user_top" class="left-category-item-hover">离线推荐榜单</router-link>
            </div>
            <div class="left-category-item">
              <router-link to="/rank/stream_rating_top" class="left-category-item-hover">实时推荐榜单</router-link>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :span="1" style="height: 100%; text-align: center">
        <el-divider direction="vertical" class="divider-style"></el-divider>
      </el-col>
      <el-col :span="18">
        <router-view></router-view>
      </el-col>
    </el-row>
    <el-backtop></el-backtop>
  </div>
</template>

<script>
import TopBar from "@/components/topbar/TopBar";
export default {
  name: "Rank",
  components: {TopBar},
  data() {
     return{

     }
  },
  methods: {
    backToHome() {
      this.$router.push('/home')
    }
  }
}
</script>

<style scoped>

.left_navi_container {
  height: 100%;
  text-align: center;
}

.left-category-title {
  font-size: 30px;
  color: black;
  font-weight: bold;
}
.left-category-item-container {
  margin-top: 30px;
}
.left-category-item {
  font-weight: bold;
  color: black;
  margin-top: 20px;
}
.left-category-item-hover:hover {
  font-weight: bold;
  color: #dc5712;
  margin-top: 20px;
}
.divider-style {
  width: 3px;
  height: 100%;
  color: #666666;
}
</style>
