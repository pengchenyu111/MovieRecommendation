<template>
  <div class="root-container-style">
    <div style="padding-top: 5vh">
      <div class="part-content-style">
        <div class="part-name-style">豆瓣</div>
        <div>
          <el-row>
            <el-col :span="12">
              <div style="margin-top: 15vh; margin-left: 15vw">
                <el-row>
                  <el-col :span="4">
                    <span style="font-size: 50px; color: #dc5712">{{ movieDetail.ratingScore }}</span>
                  </el-col>
                  <el-col :span="20">
                    <div style="margin-top: 15px">
                      <el-rate
                        :value="movieDetail.ratingStar / 10.0"
                        disabled
                        text-color="#ff9900"
                        :colors="colors"
                        score-template="{value}">
                      </el-rate>
                    </div>
                    <div style="color: #FFFFFF">{{ movieDetail.ratingNum }}人已评分</div>
                  </el-col>
                </el-row>
              </div>
            </el-col>
            <el-col :span="12">
              <div id="main" style="width: 100%; height: 300px">
                <VueEcharts :option="doubanStarRatingOption"></VueEcharts>
              </div>
            </el-col>
          </el-row>
        </div>
      </div>
    </div>
    <div style="padding-top: 5vh" v-if="movieDetail.imdbUrl !== null">
      <div class="part-content-style">
        <div class="part-name-style">IMDB</div>
        <div>
          <el-row></el-row>
          <el-row></el-row>
        </div>
      </div>
    </div>
    <div>{{ movieDetail }}</div>
    <div>{{ imdbRatingInfo }}</div>
  </div>

</template>

<script>

import * as imdbRatingApi from '@/api/rating/imdbRatingApi'

export default {
  name: "MovieStatistic",
  props: {
    movieDetail: {}
  },
  data() {
    return {
      imdbRatingInfo: {}
    }
  },
  computed: {
    doubanStarRatingOption() {
      return {
        color: ['#5470c6'],
        title: {
          text: '评分星级分布',
          textStyle: {
            color: '#FFFFFF'
          }
        },
        grid: {
          width: 300,
          height: 200
        },
        tooltip: {
        },
        xAxis: {
          type: 'value',
          name: '占比',
          nameTextStyle: {
            color: '#FFFFFF'
          },
          axisLabel: {
            color: '#FFFFFF',
            formatter:  function (value) {
              return value + '%';
            }
          }
        },
        yAxis: {
          type: 'category',
          name: '星级',
          nameTextStyle: {
            color: '#FFFFFF'
          },
          data: ['一星', '二星', '三星', '四星', '五星'],
          axisLabel: {
            color: '#f5ba30'
          }
        },
        series: [{
          name: '评分',
          type: 'bar',
          label: {
            show: true,
            position: "right",
            color: "#ff8334",
            formatter: "{c}%"
          },
          data: [
            String(this.movieDetail.rating1StarWeight).split('%')[0],
            String(this.movieDetail.rating2StarWeight).split('%')[0],
            String(this.movieDetail.rating3StarWeight).split('%')[0],
            String(this.movieDetail.rating4StarWeight).split('%')[0],
            String(this.movieDetail.rating5StarWeight).split('%')[0]
          ]
        }]
      }
    }
  },
  methods: {
    getIMDBRating() {
      imdbRatingApi.queryIMDBRating(this.movieDetail.doubanId)
        .then(res => {
          if (res.data.success) {
            this.imdbRatingInfo = res.data.data
          } else {
            this.$message.error("请求失败！")
          }
        }).catch(() => {
        this.$message.error("请求超时！")
      })
    }
  },
  created() {
    if (this.movieDetail.imdbUrl !== null) {
      this.getIMDBRating()
    }


  }
}
</script>

<style scoped>

.part-content-style {
  padding-top: 20px;
  padding-left: 20px;
  background: #333333;
  border-radius: 5px;
}

.part-name-style {
  margin-bottom: 20px;
  font-size: 30px;
  color: #FFFFFF;
}

.root-container-style {
  height: 100%;
  background: #545454;
  padding-left: 2vw;
  padding-right: 2vw;
}
</style>
