<template>
  <div class="top-bar-container">
    <img src="../../assets/img/login/login_logo.svg" class="icon-style" @click="backToHomePage">
    <span class="title-style" @click="backToHomePage">MOVIE ME</span>
    <user-avatar class="avatar-style"></user-avatar>
  </div>
</template>

<script>
import UserAvatar from "@/components/user/UserAvatar";

export default {
  name: "TopBar",
  components: {UserAvatar},
  methods: {
    backToHomePage() {
      this.$router.push('/home')
    }
  }
}
</script>

<style scoped>
.top-bar-container {
  height: 50px;
  background: #262626;
}

.icon-style {
  width: 30px;
  height: 30px;
  margin-top: 10px;
  margin-left: 10px;
}

.avatar-style {
  margin-top: 5px;
  margin-right: 10px;
  float: right;
}

.title-style {
  margin-left: 10px;
  color: #FFFFFF;
  font-size: 18px;
  font-family: Fantasy;
  font-style: italic;
}
</style>
