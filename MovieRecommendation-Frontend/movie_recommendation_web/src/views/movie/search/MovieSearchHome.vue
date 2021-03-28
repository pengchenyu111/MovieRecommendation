<template>
  <div style="height: 100%; width: 100%">
    <link rel="stylesheet" type="text/css" href="https://ianlunn.github.io/Hover/css/hover.css">
    <top-bar></top-bar>
    <div style="position:relative; height: 500px">
      <div style="height: 40vh; background: #262626; z-index: 110; "></div>
      <!--搜索框-->
      <div class="search-input-container">
        <el-input placeholder="搜索电影名/导演/演员" v-model="searchKeyword" clearable style="width: 50%">
          <el-button slot="append" icon="el-icon-search"
                     style="background: #262626"
                     @click="searchMovieByKeyword(0,18)">
          </el-button>
        </el-input>
      </div>
      <!--分类标签-->
      <div class="condition-container">
        <el-collapse accordion v-model="activeNames">
          <el-collapse-item name="1">
            <template slot="title">
              <div style="margin-left: 20px">已选:</div>
              <div style="margin-left: 20px">
                <el-tag style="margin-right: 10px" effect="dark">{{ searchRequest.types }}</el-tag>
                <el-tag style="margin-right: 10px" effect="dark">{{ searchRequest.productionCountryArea }}</el-tag>
                <el-tag style="margin-right: 10px" effect="dark">{{ searchRequest.language }}</el-tag>
              </div>
              <div style="position: absolute; right: 8%">分类查询</div>
            </template>
            <div style="margin-left: 20px; margin-top: 15px">
              <el-row>
                <el-col :span="2">类型：</el-col>
                <el-col :span="22" style="color: #545454">
                  <span v-for="item in movieTags"
                        :key="item"
                        @click="tagClick(item)"
                        class="condition-tag-style">
                    {{ item }}
                  </span>
                </el-col>
              </el-row>
            </div>
            <div style="margin-left: 20px; margin-top: 15px">
              <el-row>
                <el-col :span="2">地区：</el-col>
                <el-col :span="22" style="color: #545454">
                  <span v-for="item in productionCountryAreas"
                        :key="item"
                        @click="areaClick(item)"
                        class="condition-tag-style">
                    {{ item }}
                  </span>
                </el-col>
              </el-row>
            </div>
            <div style="margin-left: 20px; margin-top: 15px">
              <el-row>
                <el-col :span="2">语言：</el-col>
                <el-col :span="22" style="color: #545454">
                  <span v-for="item in languages"
                        :key="item"
                        @click="languageClick(item)"
                        class="condition-tag-style">
                    {{ item }}
                  </span>
                </el-col>
              </el-row>
            </div>
            <div style="margin-left: 20px; margin-top: 15px">
              <el-row>
                <el-col :span="2">评分区间：</el-col>
                <el-col :span="12" style="color: #545454">
                  <el-slider v-model="ratingRange"
                             @change="ratingRangeChange"
                             range
                             show-stops
                             :marks="marks"
                             :min="0"
                             :max="10"
                             :step="0.1">
                  </el-slider>
                </el-col>
                <el-col :span="10" style="color: #5699dd; padding-left: 20px; padding-top: 5px">
                  已选{{ searchRequest.ratingScoreLowerBound }}--{{ searchRequest.ratingScoreUpperBound }}分
                </el-col>
              </el-row>
            </div>
          </el-collapse-item>
        </el-collapse>
      </div>
      <!--展示列表-->
      <div class="movie-list-container">
        <el-container v-loading="isLoading"
                      element-loading-text="拼命加载中"
                      element-loading-spinner="el-icon-loading"
                      element-loading-background="rgba(0, 0, 0, 0.8)">
          <el-row :gutter="20" style="margin-top: 50px">
            <el-col :span="4" v-for="item in movieDetailList" :key="item.doubanId">
              <movie-search-item :movie-detail="item"
                                 class="movie-item-style">
              </movie-search-item>
            </el-col>
          </el-row>
        </el-container>
      </div>
      <!--分页导航栏-->
      <div style="text-align: center">
        <el-pagination
          :hide-on-single-page="isHidePagination"
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage"
          :page-size="18"
          layout="prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </div>
      <!--回到顶部-->
      <el-backtop></el-backtop>
    </div>
  </div>
</template>

<script>
import TopBar from "@/components/topbar/TopBar";
import * as movieDetailApi from '@/api/movie/movieDetailApi'
import MovieSearchItem from "@/components/movie/MovieSearchItem";

export default {
  name: "MovieSearchHome",
  components: {MovieSearchItem, TopBar},
  data() {
    return {
      searchKeyword: '',
      activeNames: ['1'],
      movieTags: this.$store.state.movieTags,
      productionCountryAreas: this.$store.state.productionCountryArea,
      languages: this.$store.state.language,
      searchRequest: {
        language: "汉语普通话",
        pageNum: 0,
        pageSize: 18,
        productionCountryArea: "中国大陆",
        ratingScoreLowerBound: 0,
        ratingScoreUpperBound: 10,
        types: "爱情"
      },
      ratingRange: [0, 10],
      marks: {
        0: '0',
        2.5: '2.5',
        5: '5.0',
        7.5: '7.5',
        10: '10',
      },
      isLoading: false,
      movieDetailList: [],
      searchFlag: 0,
      isHidePagination: true,
      total: 0,
      currentPage: 1
    }
  },
  methods: {
    searchMovieByKeyword(pageNum, pageSize) {
      if (this.searchKeyword === '') {
        this.$message.error("请输入搜索内容！")
      } else {
        this.isLoading = true
        this.searchFlag = 1
        this.searchMovie(pageNum, pageSize)
      }
    },
    tagClick(item) {
      this.searchRequest.types = item
      this.searchByTag(0, 18)
    },
    areaClick(item) {
      this.searchRequest.productionCountryArea = item
      this.searchByTag(0, 18)
    },
    languageClick(item) {
      this.searchRequest.language = item
      this.searchByTag(0, 18)
    },
    ratingRangeChange() {
      this.searchRequest.ratingScoreLowerBound = this.ratingRange[0]
      this.searchRequest.ratingScoreUpperBound = this.ratingRange[1]
      this.searchByTag(0, 18)
    },
    handleCurrentChange(val) {
      if (this.searchFlag === 0) {
        this.searchByTag(val - 1., 18)
      } else if (this.searchFlag === 1) {
        this.searchMovieByKeyword(val - 1., 18)
      }
    },
    searchMovie(pageNum, pageSize) {
      movieDetailApi.searchMovie(pageNum, pageSize, this.searchKeyword)
        .then(res => {
          if (res.data.success) {
            this.movieDetailList = res.data.data.resultList
            this.total = res.data.data.total
            this.isLoading = false
            this.isHidePagination = false
          }
        }).catch(() => {
        this.$message.error("请求超时！")
        this.isLoading = false
      })
    },
    searchByTag(pageNum, pageSize) {
      this.searchFlag = 0
      this.searchRequest.pageNum = pageNum
      this.searchRequest.pageSize = pageSize
      movieDetailApi.searchByTags(this.searchRequest)
        .then(res => {
          if (res.data.success) {
            this.movieDetailList = res.data.data.resultList
            this.total = res.data.data.total
            this.isLoading = false
            this.isHidePagination = false
          }
        })
        .catch(() => {
          this.$message.error("请求超时！")
          this.isLoading = false
        })
    }
  },
  created() {
    this.searchByTag(0, 18)
  }
}
</script>

<style scoped>
.search-input-container {
  width: 100%;
  margin-top: -30vh;
  text-align: center;
}

.condition-container {
  margin-top: 5vh;
  margin-right: 5vw;
  margin-left: 5vw;
  background: #FFFFFF;
  border-radius: 10px;
}

.condition-tag-style {
  margin-top: 5px;
  margin-right: 10px;
  zoom: 1;
  cursor: pointer;
  border-radius: 2px;
}

.condition-tag-style:hover {
  color: #127bab;
}

.movie-list-container {
  background: #FFFFFF;
  padding-left: 1vw;
  margin-left: 5vw;
  margin-right: 5vw;
}

.movie-item-style {
}
</style>
