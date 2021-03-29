<template>
  <div style="width: 100%; height: 150px; margin-top: 20px">
    <el-row>
      <!--次序-->
      <el-col :span="1">
        <div class="rank-order" :class="activeOrderBg(index)">
          {{ index }}
        </div>
      </el-col>
      <!--封面-->
      <el-col :span="3" style="text-align: center">
        <el-image :src="movieDetail.coverUrl" style="height: 150px; width: 100px" class="grow-shadow" @click="toMovieDetailPage(movieDetail.doubanId)">
          <div slot="error" style="text-align: center; line-height: 150px">
            <i class="el-icon-picture-outline"></i>
          </div>
        </el-image>
      </el-col>
      <!--内容-->
      <el-col :span="20">
        <el-row>
          <el-col :span="18">
            <div @click="toMovieDetailPage(movieDetail.doubanId)" class="title-style">{{ movieDetail.title }}</div>
            <el-row>
              <el-col :span="2"><span class="prop-style">导演：</span></el-col>
              <el-col :span="22"><span class="prop-content-style">{{ movieDetail.directors }}</span></el-col>
            </el-row>
            <el-row>
              <el-col :span="2"><span class="prop-style">主演：</span></el-col>
              <el-col :span="22"><span class="prop-content-style">{{ movieDetail.casts }}</span></el-col>
            </el-row>
            <el-row>
              <el-col :span="2"><span class="prop-style">类型：</span></el-col>
              <el-col :span="22"><span class="prop-content-style">{{ movieDetail.types }}</span></el-col>
            </el-row>
          </el-col>
          <!--评分情况-->
          <el-col :span="6">
            <div class="rating-score-style">
              {{ movieDetail.ratingScore }}
            </div>
            <div class="rating-star-style">
              <el-rate
                v-model="ratingStarNum"
                disabled
                show-score
                :colors="colors"
                text-color="#ff9900">
              </el-rate>
            </div>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "RankListItem",
  props: {
    index: Number,
    movieDetail: {}
  },
  data() {
    return {
      colors: ['#99A9BF', '#F7BA2A', '#FF9900']
    }
  },
  computed: {
    activeOrderBg() {
      return (index) => {
        if (index === 1) {
          return 'rank-order-bg-1'
        } else if (index === 2) {
          return 'rank-order-bg-2'
        } else if (index === 3) {
          return 'rank-order-bg-3'
        } else {
          return 'rank-order-bg-normal'
        }
      }
    },
    ratingStarNum() {
      return this.movieDetail.ratingStar / 10.0
    }
  },
  methods: {
    toMovieDetailPage(doubanId) {
      let href = this.$router.resolve({path: `/movie/${doubanId}`});
      window.open(href.href, '_blank')
    }
  }
}
</script>

<style scoped>
.rank-order {
  height: 150px;
  width: 80%;
  line-height: 150px;
  text-align: center;
  font-size: 25px;
  font-family: Fantasy;
  color: #000000;
}

.title-style {
  color: black;
  margin-bottom: 10px;
}

.title-style:hover {
  color: dodgerblue;
  margin-bottom: 10px;
}

.rank-order-bg-1 {
  background: #fe5a35;
}

.rank-order-bg-2 {
  background: #feb12b;
}

.rank-order-bg-3 {
  background: #35c096;
}

.rank-order-bg-normal {
  background: #959595;
}

.prop-content-style {
  font-size: 14px;
}

.prop-style {
  width: 50px;
  font-size: 14px;
}

.rating-score-style {
  font-size: 40px;
  text-align: center;
  line-height: 100px;
  font-family: Fantasy;
  color: #dc5712;
}

.rating-star-style {
  text-align: center;
}

.grow-shadow {
  display: inline-block;
  vertical-align: middle;
  -webkit-transform: perspective(1px) translateZ(0);
  transform: perspective(1px) translateZ(0);
  box-shadow: 0 0 1px rgba(0, 0, 0, 0);
  -webkit-transition-duration: 0.3s;
  transition-duration: 0.3s;
  -webkit-transition-property: box-shadow;
  transition-property: box-shadow;
}
.grow-shadow:hover {
  box-shadow: 0 5px 5px 3px rgba(0, 0, 0, 0.6);
  -webkit-transform: scale(1.05);
  transform: scale(1.05);
}
</style>
