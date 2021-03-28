<template>
  <div class="item-container">
    <link rel="stylesheet" type="text/css" href="https://ianlunn.github.io/Hover/css/hover.css">
    <el-image :src="movieDetail.coverUrl"
              @click="toMovieDetailPage"
              class="item-img-style hvr-grow-shadow">
    </el-image>
    <div class="item-title-style">
      {{ getTitle }}
      <span style="color: #dc5712">{{ movieDetail.ratingScore }}</span>
    </div>
  </div>
</template>

<script>
export default {
  name: "MovieSearchItem",
  props: {
    movieDetail: {}
  },
  methods: {
    toMovieDetailPage() {
      let href = this.$router.resolve({path: `/movie/${this.movieDetail.doubanId}`});
      window.open(href.href, '_blank')
    }
  },
  computed: {
    getTitle() {
      return this.movieDetail.title.split(",")[0]
    }
  }
}
</script>

<style scoped>
.item-container {
  width: 170px;
  height: 256px;
  margin-right: 10px;
  margin-bottom: 80px;
}

.item-title-style {
  margin-top: 20px;
}

.item-img-style {
  height: 240px;
  width: 170px;
  cursor: pointer;
}

</style>
