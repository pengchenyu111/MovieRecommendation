<template>
  <span>
    <el-dropdown placement="bottom" @command="handleCommand">
      <span class="el-dropdown-link">
        <el-avatar :src="userInfo.userHeadPortraitUrl"></el-avatar>
      </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>
            <div style="text-align: center; font-size: 18px; font-weight: bolder">{{ userInfo.userName }}</div>
          </el-dropdown-item>
          <el-dropdown-item icon="el-icon-user" command="profile">个人主页</el-dropdown-item>
          <el-dropdown-item icon="el-icon-collection" command="userCollection">我的收藏</el-dropdown-item>
          <el-dropdown-item icon="el-icon-edit-outline" command="userReview">我的评论</el-dropdown-item>
          <el-dropdown-item icon="el-icon-close" command="exit">退出</el-dropdown-item>
        </el-dropdown-menu>
    </el-dropdown>
  </span>
</template>

<script>
export default {
  name: "UserAvatar",
  data() {
    return {
      userInfo: JSON.parse(sessionStorage.getItem("currentUser"))
    }
  },
  methods: {
    handleCommand(command) {
      if (command === 'profile') {
        // 跳转到个人主页
        let href = this.$router.resolve({path: `/user/profile/${this.userInfo.userId}`});
        window.open(href.href, '_blank')
      } else if (command === 'userCollection') {
        // todo 跳转到我的收藏
        this.$message.error("暂未开放！")
      } else if (command === 'userReview') {
        // 跳转到我的评论
        let href = this.$router.resolve({path: `/user/myReview/${this.userInfo.userId}`});
        window.open(href.href, '_blank')
      } else if (command === 'exit') {
        // 退出登录
        this.$router.replace('/login');
      }
    }
  }
}
</script>

<style scoped>

</style>
