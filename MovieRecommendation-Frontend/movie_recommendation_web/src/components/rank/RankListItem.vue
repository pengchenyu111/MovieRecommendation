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
        <el-image :src="movieDetail.coverUrl" style="height: 150px; width: 100px">
          <div slot="error" style="text-align: center; line-height: 150px">
            <i class="el-icon-picture-outline"></i>
          </div>
        </el-image>
      </el-col>
      <!--内容-->
      <el-col :span="20">
        <el-row>
          <el-col :span="18">
            <div style="color: black; margin-bottom: 10px">{{ movieDetail.title }}</div>
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
</style>
