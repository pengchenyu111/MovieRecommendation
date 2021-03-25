<template>
  <div class="stream-rating-top-container">
    <rank-list-item
      v-for="(item, index) in movieDetailList"
      :key="index"
      :movie-detail="item"
      :index="index + 1 ">
    </rank-list-item>
  </div>
</template>

<script>
import RankListItem from "@/components/rank/RankListItem";
import * as recommenderApi from "@/api/recommend/recommenderApi";

export default {
  name: "StreamRatingTop",
  components: {
    RankListItem
  },
  data() {
    return {
      movieDetailList: [],
      userInfo: JSON.parse(sessionStorage.getItem("currentUser"))
    }
  },
  created() {
    recommenderApi.queryStreamRatingRecs(this.userInfo.userId)
      .then(res => {
        this.movieDetailList = res.data.data
      }).catch(err => {
      console.log(err);
    })

  }
}
</script>

<style scoped>
.stream-rating-top-container {
  margin-top: 5vh;
}
</style>
