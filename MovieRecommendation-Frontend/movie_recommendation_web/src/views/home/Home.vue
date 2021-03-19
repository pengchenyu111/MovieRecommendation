<template>
  <div>
    <v-chip-group v-model="dialogVisible" column multiple>
      <v-chip filter outlined>Elevator</v-chip>
      <v-chip filter outlined>Elevator</v-chip>
      <v-chip filter outlined>Elevator</v-chip>
      <v-chip filter outlined>Elevator</v-chip>
    </v-chip-group>
  </div>

</template>

<script>
import * as userTagPrefer from "@/api/tag/tagPrefer";
import * as tag from "@/api/tag/tag";
import colorUtil from "@/common/utils/colorUtil";

export default {
  name: "Home",
  data() {
    return {
      userInfo: JSON.parse(sessionStorage.getItem("currentUser")),
      dialogVisible: true,
      checkboxGroup: [],
      tagList: [],
      checkboxBgColor: {backgroundColor: colorUtil.randomColorPicker()}
    }
  },
  created() {
    userTagPrefer.queryUserTagPreferById(this.userInfo.userId)
      .then(res => {
        if (!res.data.success) {
          // 如果用户没有喜好标签数据，则打开对话框让用户选择
          this.dialogVisible = true;
        }
      }).catch(err => {
      console.log(err);
    })
    tag.queryAllTags()
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
.dialog-footer {
  margin-top: 80px;
}
</style>
