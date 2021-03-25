<template>
  <div class="per-tag-top-container">
    <el-collapse accordion>
      <el-collapse-item>
        <template slot="title">
          <div>
            默认展示您喜好的标签：
            <el-tag style="margin-right: 5px"
                    type="success"
                    v-for="item in userPreferTags"
                    :key="item.tagId">
              {{ item.tagName }}
            </el-tag>
            <span style="position: absolute; right: 50px">点击获取更多</span>
          </div>
        </template>
        <div>
          <el-tag style="margin-right: 10px; margin-top: 10px"
                  type="warning"
                  v-for="item in allTagList"
                  :key="item.tagId"
                  @click="currentTagTop(item.tagName)">
            {{ item.tagName }}
          </el-tag>
        </div>
      </el-collapse-item>
    </el-collapse>
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
import * as recommenderApi from "@/api/recommend/recommenderApi";
import * as userTagPreferApi from "@/api/tag/tagPreferApi";
import * as tagApi from "@/api/tag/tagApi";

export default {
  name: "PerTagTop",
  components: {
    RankListItem
  },
  data() {
    return {
      movieDetailList: [],
      userInfo: JSON.parse(sessionStorage.getItem("currentUser")),
      allTagList: [],
      userPreferTags: []
    }
  },
  computed: {}
  ,
  methods: {
    currentTagTop(tagName) {
      recommenderApi.queryPerTagRecs(tagName)
        .then(res => {
          this.movieDetailList = res.data.data
        }).catch(err => {
        console.log(err);
      })
    }
  },
  created() {
    recommenderApi.queryUserPreferGenreRecs(this.userInfo.userId)
      .then(res => {
        this.movieDetailList = res.data.data
      }).catch(err => {
      console.log(err);
    })
    //以下两个方法是为了加载用户喜好的电影分类
    tagApi.queryAllTags()
      .then(res => {
        this.allTagList = res.data.data
      }).catch(err => {
      console.log(err);
    })
    userTagPreferApi.queryUserTagPreferDetailById(this.userInfo.userId)
      .then(res => {
        this.userPreferTags = res.data.data
      }).catch(err => {
      console.log(err);
    })

  }
}
</script>

<style scoped>
.per-tag-top-container {
  margin-top: 5vh;
}
</style>
