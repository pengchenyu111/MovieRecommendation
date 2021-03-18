<template>
  <div class="fixed-background" :style="loginBgUrl">
    <div class="title">
      <img src="../assets/img/login/login_logo.svg">
      <div class="title-text">MOVIE ME</div>
    </div>
    <div class="login-box-bg"/>
    <login-box class="login-box"/>
    <div class="bottom-words">电 影 就 是 我 们，我 们 就 是 电 影</div>
  </div>
</template>

<script>
import ImgConstants from "@/common/constant/ImgConstants";
import LoginBox from "@/components/login/LoginBox";

export default {
  name: "Login",
  components: {LoginBox},
  data() {
    return {
      loginBgUrl: {backgroundImage: 'url(' + ImgConstants.LOGIN_BACKGROUND + ')'}
    }
  },
  methods: {

  }
}
</script>

<style scoped>

.fixed-background {
  width: 100vw;
  height: 100vh;
  position: fixed;
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
}

.title {
  top: 8vh;
  position: absolute;
  left: 69%;
}

.title-text {
  float: right;
  margin-top: 28px;
  margin-left: 20px;
  font-size: 30px;
  font-family: Fantasy;
  color: white;
  font-style: italic;
}

.login-box, .login-box-bg {
  top: 20vh;
  position: absolute;
  left: 78%;
  margin-left: -9vw;
  z-index: -1;
}

.login-box {
  width: 24vw;
  left: 81%;
}

.login-box-bg {
  background-color: #666666;
  border-radius: 5px;
  opacity: 0.6;
  height: 44vh;
  width: 30vw;
  filter: blur(1px);
  overflow: hidden;
}

.bottom-words {
  position: absolute;
  top: 95vh;
  color: white;
  left: 80%;
}
</style>
