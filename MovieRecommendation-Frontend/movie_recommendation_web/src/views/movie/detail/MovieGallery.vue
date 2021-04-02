<template>
  <div style="width: 100%; height: 100vh; background: #545454">
    <link rel="stylesheet" type="text/css" href="https://ianlunn.github.io/Hover/css/hover.css">
    <div class="img-container">
      <div class="no-img-style" v-if="getImgUrlList === null">暂无图片</div>
      <el-row :gutter="20">
        <el-col :span="4" v-for="(item, index) in getImgUrlList" :key="index">
          <el-image :src="item" class="img-style hvr-grow-shadow" fit="fill">
            <div slot="error" style="text-align: center; line-height: 150px">
              <i class="el-icon-picture-outline"></i>
            </div>
          </el-image>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
export default {
  name: "MovieGallery",
  props: {
    movieDetail: {}
  },
  data() {
    return {}
  },
  computed: {
    getImgUrlList() {

      return String(this.movieDetail.imgList).split(",")
    }
  }
}
</script>

<style scoped>
.img-container {
  padding-top: 5vh;
  padding-left: 2vw;
  padding-right: 2vw;
}

.no-img-style {
  color: #FFFFFF;
  font-size: 25px;
  text-align: center;
}

.img-style {
  height: 200px;
  width: 200px;
  margin-bottom: 30px;
}
</style>
