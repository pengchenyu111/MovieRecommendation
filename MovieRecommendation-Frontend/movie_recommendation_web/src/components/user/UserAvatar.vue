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
        // todo 跳转到个人主页
        console.log('aaaaaaa')
      } else if (command === '') {
        // todo 跳转到我的收藏
        console.log('bbbb')
      } else if (command === '') {
        // todo 跳转到我的评论
        console.log('cccc')
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
