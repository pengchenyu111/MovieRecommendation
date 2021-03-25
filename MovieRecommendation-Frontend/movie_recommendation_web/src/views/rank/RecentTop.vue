<template>
  <div class="recent-top-container">
    <rank-list-item
      v-for="(item, index) in movieDetailList"
      :key="index"
      :movie-detail="item"
      :index="index + 1 ">
    </rank-list-item>
  </div>
</template>

<script>
import * as recommenderApi from "@/api/recommend/recommenderApi";
import RankListItem from "@/components/rank/RankListItem";

export default {
  name: "RecentTop",
  components:{
    RankListItem
  },
  data() {
    return {
      movieDetailList: []
    }
  },
  created() {
    recommenderApi.queryRecentlyTop20()
      .then(res => {
        this.movieDetailList = res.data.data
      }).catch(err => {
      console.log(err);
    })
  }
}
</script>

<style scoped>
.recent-top-container {
  margin-top: 5vh;
}
</style>
