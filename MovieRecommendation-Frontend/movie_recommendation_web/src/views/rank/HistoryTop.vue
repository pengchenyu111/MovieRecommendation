<template>
  <div class="history-page-container">
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
import * as recommenderApi from "@/api/recommend/recommenderApi"

export default {
  name: "HistoryTop",
  components: {RankListItem},
  data() {
    return {
      movieDetailList: {}
    }
  },
  methods: {},
  created() {
    recommenderApi.queryHistoryTop20()
      .then(res => {
        this.movieDetailList = res.data.data
      }).catch(err => {
      console.log(err);
    })
  }
}
</script>

<style scoped>
.history-page-container {
  margin-top: 5vh;
}
</style>
