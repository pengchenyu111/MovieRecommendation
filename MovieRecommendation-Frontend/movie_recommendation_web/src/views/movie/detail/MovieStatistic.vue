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
    <div style="padding-top: 5vh;padding-bottom: 100px" v-if="movieDetail.imdbUrl !== null">
      <div class="part-content-style">
        <div class="part-name-style">IMDB</div>
        <div>
          <el-row>
            <el-col :span="6">
              <div style="margin-top: 15vh; margin-left: 20px">
                <el-row>
                  <el-col :span="8">
                    <span style="font-size: 50px; color: #dc5712">{{ imdbRatingInfo.imdbRating }}</span>
                  </el-col>
                  <el-col :span="12">
                    <div style="margin-top: 15px">
                      <el-rate
                        :value="getIMDBRatingStar"
                        disabled
                        text-color="#ff9900"
                        :colors="colors"
                        score-template="{value}">
                      </el-rate>
                    </div>
                    <div style="color: #FFFFFF">{{ imdbTotalVotes }}人已评分</div>
                  </el-col>
                </el-row>
              </div>
            </el-col>
            <el-col :span="10">
              <div id="imdb_rating_main" style="width: 100%; height: 300px">
                <VueEcharts :option="imdbStartRatingOption"></VueEcharts>
              </div>
            </el-col>
            <el-col :span="8">
              <div id="imdb_rating_pie_main" style="width: 100%; height: 300px">
                <VueEcharts :option="imdbStartRatingPieOption"></VueEcharts>
              </div>
            </el-col>
          </el-row>
          <el-row style="margin-top: 50px">
            <el-col :span="12">
              <div id="imdb_age_pie" style="width: 100%; height: 500px">
                <VueEcharts :option="imdbAgeRatingPieOption"></VueEcharts>
              </div>
            </el-col>
            <el-col :span="12">
              <div id="imdb_sex" style="width: 100%; height: 500px">
                <VueEcharts :option="imdbSexRatingOption"></VueEcharts>
              </div>
            </el-col>
          </el-row>
        </div>
      </div>
    </div>
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
      imdbRatingInfo: {},
      imdbTotalVotes: 0,
      colors: ['#99A9BF', '#F7BA2A', '#FF9900'],
    }
  },
  computed: {
    // https://echarts.apache.org/zh/option.html#grid
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
        tooltip: {},
        xAxis: {
          type: 'value',
          name: '占比',
          nameTextStyle: {
            color: '#FFFFFF'
          },
          axisLabel: {
            color: '#FFFFFF',
            formatter: function (value) {
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
    },
    getIMDBRatingStar() {
      return this.imdbRatingInfo.imdbRating / 10 * 5.0
    },
    imdbStartRatingOption() {
      return {
        color: ['#fac858'],
        title: {
          text: '评分星级分布',
          left: 'center',
          textStyle: {
            color: '#FFFFFF'
          }
        },
        grid: {
          width: 450,
          height: 200
        },
        tooltip: {},
        xAxis: {
          type: 'category',
          name: '星级',
          nameTextStyle: {
            color: '#FFFFFF'
          },
          data: ['一星', '二星', '三星', '四星', '五星', '六星', '七星', '八星', '九星', '十星'],
          axisLabel: {
            color: '#FFFFFF'
          }
        },
        yAxis: {
          type: 'value',
          name: '评分数',
          nameTextStyle: {
            color: '#FFFFFF'
          },
          axisLabel: {
            color: '#f5ba30',
            formatter: function (value) {
              return value / 10000.0 + '万'
            }
          }
        },
        series: [{
          name: '评分',
          type: 'bar',
          label: {
            show: true,
            position: "top",
            color: "#ff8334",
            formatter: "{c}人"
          },
          data: [
            this.imdbRatingInfo.allRangeRatingScoreWeightVote.ratingScoreWeightVote1.ratingScoresVotes,
            this.imdbRatingInfo.allRangeRatingScoreWeightVote.ratingScoreWeightVote2.ratingScoresVotes,
            this.imdbRatingInfo.allRangeRatingScoreWeightVote.ratingScoreWeightVote3.ratingScoresVotes,
            this.imdbRatingInfo.allRangeRatingScoreWeightVote.ratingScoreWeightVote4.ratingScoresVotes,
            this.imdbRatingInfo.allRangeRatingScoreWeightVote.ratingScoreWeightVote5.ratingScoresVotes,
            this.imdbRatingInfo.allRangeRatingScoreWeightVote.ratingScoreWeightVote6.ratingScoresVotes,
            this.imdbRatingInfo.allRangeRatingScoreWeightVote.ratingScoreWeightVote7.ratingScoresVotes,
            this.imdbRatingInfo.allRangeRatingScoreWeightVote.ratingScoreWeightVote8.ratingScoresVotes,
            this.imdbRatingInfo.allRangeRatingScoreWeightVote.ratingScoreWeightVote9.ratingScoresVotes,
            this.imdbRatingInfo.allRangeRatingScoreWeightVote.ratingScoreWeightVote10.ratingScoresVotes,
          ]
        }]
      }
    },
    imdbStartRatingPieOption() {
      return {
        title: {
          text: '评分星级分布',
          left: 'center',
          textStyle: {
            color: '#FFFFFF'
          }
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          top: '30',
          right: 'right',
          textStyle: {
            color: '#FFFFFF'
          }
        },
        series: [
          {
            name: '占比',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '20',
                fontWeight: 'bold',
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(255,255,255,0.5)'
              }
            },
            labelLine: {
              show: false
            },
            data: [
              {
                value: this.imdbRatingInfo.allRangeRatingScoreWeightVote.ratingScoreWeightVote1.ratingScoresVotes,
                name: '一星'
              },
              {
                value: this.imdbRatingInfo.allRangeRatingScoreWeightVote.ratingScoreWeightVote2.ratingScoresVotes,
                name: '二星'
              },
              {
                value: this.imdbRatingInfo.allRangeRatingScoreWeightVote.ratingScoreWeightVote3.ratingScoresVotes,
                name: '三星'
              },
              {
                value: this.imdbRatingInfo.allRangeRatingScoreWeightVote.ratingScoreWeightVote4.ratingScoresVotes,
                name: '四星'
              },
              {
                value: this.imdbRatingInfo.allRangeRatingScoreWeightVote.ratingScoreWeightVote5.ratingScoresVotes,
                name: '五星'
              },
              {
                value: this.imdbRatingInfo.allRangeRatingScoreWeightVote.ratingScoreWeightVote6.ratingScoresVotes,
                name: '六星'
              },
              {
                value: this.imdbRatingInfo.allRangeRatingScoreWeightVote.ratingScoreWeightVote7.ratingScoresVotes,
                name: '七星'
              },
              {
                value: this.imdbRatingInfo.allRangeRatingScoreWeightVote.ratingScoreWeightVote8.ratingScoresVotes,
                name: '八星'
              },
              {
                value: this.imdbRatingInfo.allRangeRatingScoreWeightVote.ratingScoreWeightVote9.ratingScoresVotes,
                name: '九星'
              },
              {
                value: this.imdbRatingInfo.allRangeRatingScoreWeightVote.ratingScoreWeightVote10.ratingScoresVotes,
                name: '十星'
              },
            ]
          }
        ]
      }
    },
    imdbAgeRatingPieOption() {
      return {
        title: {
          text: '评分年龄分布',
          left: 'center',
          textStyle: {
            color: '#FFFFFF'
          }
        },
        grid: {
          width: 300,
          height: 500
        },
        legend: {
          top: 'bottom',
          textStyle: {
            color: '#FFFFFF'
          }
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        series: [
          {
            name: '占比',
            type: 'pie',
            radius: [30, 150],
            center: ['50%', '50%'],
            //roseType: 'area',
            itemStyle: {
              borderRadius: 8
            },
            data: [
              {
                value: this.imdbRatingInfo.allAgeRatingVote.ageLessThan18.vote,
                name: '小于18岁，评分：' + this.imdbRatingInfo.allAgeRatingVote.ageLessThan18.scores,
                label: {color: "#FFFFFF"}
              },
              {
                value: this.imdbRatingInfo.allAgeRatingVote.age1829.vote,
                name: '18岁~29岁' + this.imdbRatingInfo.allAgeRatingVote.age1829.scores,
                label: {color: "#FFFFFF"}
              },
              {
                value: this.imdbRatingInfo.allAgeRatingVote.age3044.vote,
                name: '30岁~44岁' + this.imdbRatingInfo.allAgeRatingVote.age3044.scores,
                label: {color: "#FFFFFF"}
              },
              {
                value: this.imdbRatingInfo.allAgeRatingVote.ageMoreThan45.vote,
                name: '大于45岁' + this.imdbRatingInfo.allAgeRatingVote.ageMoreThan45.scores,
                label: {color: "#FFFFFF"}
              }
            ]
          }
        ]

      }
    },
    imdbSexRatingOption() {
      return {
        title: {
          text: '评分性别分布',
          left: 'center',
          textStyle: {
            color: '#FFFFFF'
          }
        },
        grip: {
          width: 500,
          height: 300
        },
        tooltip: {},
        xAxis: {
          splitLine: {show: false},
          offset: 10,
          axisLine: {
            lineStyle: {
              color: '#ffffff'
            }
          },
          axisLabel: {
            show: false
          }
        },
        yAxis: {
          data: ['男性', '女性'],
          inverse: true,
          axisTick: {show: false},
          axisLine: {show: false},
          axisLabel: {
            margin: 10,
            color: '#ffffff',
            fontSize: 16
          }
        },
        series: [{
          // current data
          type: 'pictorialBar',
          symbol: 'image://http://81.70.252.155:8000/movierecommendation/icon/people.png',
          symbolRepeat: 'fixed',
          symbolMargin: '5%',
          symbolClip: true,
          symbolSize: 30,
          label: {
            show: true,
            formatter: function (params) {
                return '综合评分: ' + params.name
            },
            position: 'insideBottom',
            color: '#FFFFFF',
            fontSize: 18
          },
          data: [
            {value: this.imdbRatingInfo.allSexRatingVote.maleRatings.vote, name: String(this.imdbRatingInfo.allSexRatingVote.maleRatings.scores)},
            {value: this.imdbRatingInfo.allSexRatingVote.femaleRatings.vote, name: String(this.imdbRatingInfo.allSexRatingVote.femaleRatings.scores)}
          ],
          z: 10
        }, {
          // full data
          type: 'pictorialBar',
          itemStyle: {
            normal: {
              opacity: 0.2
            }
          },
          animationDuration: 0,
          symbolRepeat: 'fixed',
          symbolMargin: '5%',
          symbol: 'image://http://81.70.252.155:8000/movierecommendation/icon/people.png',
          symbolSize: 30,
          data: [
            {value: this.imdbRatingInfo.allSexRatingVote.maleRatings.vote, name: String(this.imdbRatingInfo.allSexRatingVote.maleRatings.scores)},
            {value: this.imdbRatingInfo.allSexRatingVote.femaleRatings.vote, name: String(this.imdbRatingInfo.allSexRatingVote.femaleRatings.scores)}
          ],
          z: 2
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
    },
    getTotalVotes() {
      imdbRatingApi.queryIMDBVotes(this.movieDetail.doubanId)
        .then(res => {
          if (res.data.success) {
            this.imdbTotalVotes = res.data.data
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
      this.getTotalVotes()
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
