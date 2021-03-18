<template>
  <el-form class="inputLogin">
    <el-input
      v-model="account"
      placeholder="账号"
      auto-complete="new-password"
      size="large"
      prefix-icon="el-icon-user">
    </el-input>
    <el-input
      v-model="password"
      placeholder="密码"
      size="large"
      prefix-icon="el-icon-key"
      show-password>
    </el-input>
    <div class="others">
      <el-link :underline="false" class="others-item" style="float: left">现在注册</el-link>
      <el-link :underline="false" class="others-item" style="float: right">忘记密码</el-link>
    </div>
    <el-button
      class="loginBtn"
      type="primary"
      size="medium"
      @click="login">
      登录
    </el-button>
  </el-form>
</template>

<script>
export default {
  name: "LoginBox",
  data() {
    return {
      account: '',
      password: ''
    }
  },
  methods: {
    login() {
      console.log('dlogin');
    }
  }
}
</script>

<style scoped>
.inputLogin {
  margin: 15px auto;
  width: 150px;
  height: 320px;
  text-align: center;
}

.inputLogin div {
  margin: 15px auto;
}

.loginBtn {
  width: 16vw;
  margin-top: 30px;
}

.others-item {
  margin: 0 10px 0 10px;
  color: white;
}
</style>
