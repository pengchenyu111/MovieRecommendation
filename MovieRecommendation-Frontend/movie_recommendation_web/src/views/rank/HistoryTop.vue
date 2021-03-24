<template>
  <div class="history-page-container">
    <rank-list-item></rank-list-item>
  </div>
</template>

<script>
import RankListItem from "@/components/rank/RankListItem";
export default {
  name: "HistoryTop",
  components: {RankListItem}
}
</script>

<style scoped>
.history-page-container {
  margin-top: 80px;
}
</style>
