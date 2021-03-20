<template>
  <el-dialog title="选择喜欢的电影分类标签" :visible.sync="visible">
    <v-chip-group column multiple color="#725334">
      <v-chip
        filter
        v-for="(item, index) in tagList"
        :key="item.tagId"
        @click="addToUserPrefer(item.tagId)"
        :class="chipColorList[index % 5]">
        {{ item.tagName }}
      </v-chip>
    </v-chip-group>
    <div class="dialog-footer">
      <el-button type="info" icon="el-icon-close" @click="visible = false">
        取 消
      </el-button>
      <el-button type="primary" icon="el-icon-check" @click="submitUserPrefer">
        确 认
      </el-button>
    </div>
  </el-dialog>

</template>

<script>

import transfer from "@/common/utils/transfer";
import * as userTagPreferApi from "@/api/tag/tagPreferApi";

export default {
  name: "MovieTag",
  props: {
    tagList: Array,
    dialogVisible: Boolean
  },
  data() {
    return {
      visible: this.dialogVisible,
      chipColorList: ["item-color1 ", "item-color2 ", "item-color3 ", "item-color4 ", "item-color5 "],
      tagTemp: []
    }
  },
  methods: {
    addToUserPrefer(tagId) {
      let index = this.tagTemp.indexOf(tagId)
      if (index !== -1) {
        this.tagTemp.splice(index, 1)
      } else {
        this.tagTemp.push(tagId)
      }
    },
    submitUserPrefer() {
      let tagList = transfer.numberListToString(this.tagTemp, ",");
      let userId = JSON.parse(sessionStorage.getItem('currentUser')).userId
      userTagPreferApi
        .addUserTagPrefer({userId: userId, tagList: tagList})
        .then(res => {
          if (res.data.success) {
            this.visible = false
            this.$message.success("提交成功！")
          }
        }).catch(() => {
        this.$message.error("提交失败！")
      })
    }
  }
}
</script>

<style scoped>

.item-color1 {
  background: #ff4364;
}

.item-color2 {
  background: #fc9d99;
}

.item-color3 {
  background: #facdae;
}

.item-color4 {
  background: #c8c7a8;
}

.item-color5 {
  background: #84af9b;
}

.dialog-footer {
  flex: 1;
  text-align: center;
  color: white;
}

</style>
