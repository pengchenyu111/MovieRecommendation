<template>
  <div style="width: 100%; height: 100%; background: #545454">
    <div class="recs-container">
      <div class="title-style">相似的电影</div>
      <div>
        <el-row :gutter="20" style="margin-top: 50px">
          <el-col :span="4" v-for="item in contentRecs" :key="item.doubanId">
            <movie-search-item :movie-detail="item"/>
          </el-col>
        </el-row>
      </div>
    </div>
    <el-divider style="color: #666666; width: 100%"></el-divider>
    <div class="recs-container">
      <div class="title-style">其他用户喜欢的电影</div>
      <div>
        <el-row :gutter="20" style="margin-top: 50px">
          <el-col :span="4" v-for="item in itemCFRecs" :key="item.doubanId">
            <movie-search-item :movie-detail="item"/>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import * as recommenderApi from '@/api/recommend/recommenderApi'
import MovieSearchItem from "@/components/movie/MovieSearchItem";

export default {
  name: "MovieSingleRecommend",
  components: {MovieSearchItem},
  data() {
    return {
      contentRecs: [],
      itemCFRecs: []
    }
  },
  created() {
    recommenderApi.queryContentRecs(this.$route.params.doubanId)
    .then(res => {
      this.contentRecs = res.data.data
    })
    recommenderApi.queryItemCFRecs(this.$route.params.doubanId)
    .then(res => {
      this.itemCFRecs = res.data.data
    })
  }
}
</script>

<style scoped>
.recs-container {
  margin-left: 2vw;
  margin-right: 2vw;
  padding-top: 5vh;
}

.title-style {
  color: #FFFFFF;
  font-size: 25px;
}
</style>
