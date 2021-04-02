<template>
  <div style="height: 100vh; background: #545454">
    <el-row>
      <el-col :span="11" style=" margin-top: 10vh">
        <div class="left-container">
          <el-image :src="movieDetail.coverUrl" fit="fill" class="movie-cover-style"></el-image>
          <img src="../../../assets/img/movie/movie-avatar-bg.png" class="movie-cover-style-bg">
          <img src="../../../assets/img/movie/movie-avatar-img-glare.png" class="movie-cover-style-glare">
          <img src="../../../assets/img/movie/movie-avatar-disc.png" class="movie-cover-style-disc">
        </div>
      </el-col>
      <el-col :span="13">
        <div class="info-container">
          <h2 class="info-title-style">{{ getTitle }}</h2>
          <div class="info-content-style">
            <el-row>
              <el-col :span="2">导演</el-col>
              <el-col :span="1" style="text-align: center">：</el-col>
              <el-col :span="21">
                <div>{{ movieDetail.directors }}</div>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="2">编剧</el-col>
              <el-col :span="1" style="text-align: center">：</el-col>
              <el-col :span="21">
                <div>{{ movieDetail.screenwriters }}</div>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="2">主演</el-col>
              <el-col :span="1" style="text-align: center">：</el-col>
              <el-col :span="21">
                <div>{{ movieDetail.casts }}</div>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="2">类型</el-col>
              <el-col :span="1" style="text-align: center">：</el-col>
              <el-col :span="21">
                <div>{{ movieDetail.types }}</div>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="2">制片国家</el-col>
              <el-col :span="1" style="text-align: center">：</el-col>
              <el-col :span="21">
                <div>{{ movieDetail.productionCountryArea }}</div>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="2">语言</el-col>
              <el-col :span="1" style="text-align: center">：</el-col>
              <el-col :span="21">
                <div>{{ movieDetail.language }}</div>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="2">上映时间</el-col>
              <el-col :span="1" style="text-align: center">：</el-col>
              <el-col :span="21">
                <div>{{ movieDetail.publishDate }}</div>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="2">片长</el-col>
              <el-col :span="1" style="text-align: center">：</el-col>
              <el-col :span="21">
                <div>{{ movieDetail.runtime }}</div>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="2">影评链接</el-col>
              <el-col :span="1" style="text-align: center">：</el-col>
              <el-col :span="21">
                <el-link class="info-link-style" :href="movieDetail.doubanUrl" target="_blank">豆瓣</el-link>
                <span> / </span>
                <el-link class="info-link-style" :href="movieDetail.imdbUrl" target="_blank">IMDB</el-link>
              </el-col>
            </el-row>
            <!--影片简介-->
            <div style="margin-top: 30px; margin-right: 15px">
              {{ movieDetail.briefInstruction }}
            </div>
            <!--评分信息-->
            <div style="margin-top: 20px">
              <el-row>
                <el-col :span="12">
                  <el-row>
                    <el-col :span="6">
                      <span style="font-size: 50px; color: #dc5712">{{ movieDetail.ratingScore }}</span>
                    </el-col>
                    <el-col :span="18">
                      <div style="margin-top: 15px">
                        <el-rate
                          :value="movieDetail.ratingStar / 10.0"
                          disabled
                          text-color="#ff9900"
                          :colors="colors"
                          score-template="{value}">
                        </el-rate>
                      </div>
                      <div>{{ movieDetail.ratingNum }}人已评分</div>
                    </el-col>
                  </el-row>
                </el-col>
                <el-col :span="12">
                  <div style="margin-top: 15px" v-if="movieDetail.betterThan !== ''">
                    <div v-for="(item, index) in getBetterThan" :key="index">
                      好于 <span style="color: #e88856">{{ item }}</span>
                    </div>
                  </div>
                </el-col>
              </el-row>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "MovieInfo",
  props: {
    movieDetail: {}
  },
  data() {
    return {
      colors: ['#99A9BF', '#F7BA2A', '#FF9900'],
    }
  },
  methods: {},
  computed: {
    getTitle() {
      let str = String(this.movieDetail.title)
      return str.split(" ")[0]
    },
    getBetterThan() {
      let str = String(this.movieDetail.betterThan)
      return str.split(" / ")
    }
  }
}
</script>

<style scoped>
.left-container {
  position: relative;
  margin-left: 5vw;
}

.movie-cover-style {
  width: 370px;
  height: 512px;
  position: absolute;
  z-index: 100;
}

.movie-cover-style-bg {
  position: absolute;
  z-index: 2;
}

.movie-cover-style-glare {
  position: absolute;
  z-index: 200;
}

.movie-cover-style-disc {
  position: absolute;
  top: 150px;
  left: 100px;
  z-index: 1;
}

.movie-cover-style-disc:hover {
  position: absolute;
  top: 150px;
  left: 180px;
  z-index: 1;
  transition: all .2s ease-in-out;
}

.info-container {
  margin-top: 10vh;
}

.info-title-style {
  font-size: 30px;
  line-height: normal;
  color: #FFFFFF;
}

.info-content-style {
  margin-top: 30px;
  font-size: 14px;
  color: #bebebe;
}

.info-link-style {
  color: #bebebe;
}

.info-link-style:hover {
  color: #5699dd;
}

</style>
