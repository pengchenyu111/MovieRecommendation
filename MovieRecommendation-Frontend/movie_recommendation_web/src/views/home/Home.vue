<template>
  <div>
    <movie-tag :tag-list="tagList" :dialog-visible="dialogVisible"/>
  </div>
</template>

<script>
import * as userTagPreferApi from "@/api/tag/tagPreferApi";
import * as tagApi from "@/api/tag/tagApi";
import MovieTag from "@/components/tag/MovieTag";

export default {
  name: "Home",
  components: {MovieTag},
  data() {
    return {
      userInfo: JSON.parse(sessionStorage.getItem("currentUser")),
      dialogVisible: false,
      tagList: [],
    }
  },
  created() {
    userTagPreferApi.queryUserTagPreferById(this.userInfo.userId)
      .then(res => {
        if (!res.data.success) {
          // 如果用户没有喜好标签数据，则打开对话框让用户选择
          this.dialogVisible = true;
        }
      }).catch(err => {
      console.log(err);
    })
    tagApi.queryAllTags()
      .then(res => {
        this.tagList = res.data.data
      }).catch(err => {
      console.log(err);
    })
  },
  methods: {}
}
</script>

<style scoped>
</style>
