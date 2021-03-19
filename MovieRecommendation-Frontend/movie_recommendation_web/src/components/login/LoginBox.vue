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
      <el-link :underline="false" class="others-item" style="float: left" @click="toRegister">现在注册</el-link>
      <el-link :underline="false" class="others-item" style="float: right" @click="toForgetPassword">忘记密码</el-link>
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
import {login} from "@/api/user/userLoginRegister";
import {ElMessage} from 'element-plus'
import * as types from "@/store/mutations_types";

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
      login(this.account, this.password)
        .then(res => {
          if (!res.data.success) {
            ElMessage.error("账号或密码错误！")
          } else {
            ElMessage.success("登录成功！")
            // 存储用户信息到Vuex
            this.$store.commit(types.SET_IS_AUTHENTICATED, true)
            this.$store.commit(types.SET_CURRENT_USER, res.data.data)
            // 存储用户信息到sessionStorage
            sessionStorage.setItem("isAuthenticated", "true")
            sessionStorage.setItem("currentUser", JSON.stringify(res.data.data))
            this.$router.replace('/home')
          }
        }).catch(err => {
        console.log(err);
      })
    },
    toRegister() {
      let href = this.$router.resolve({path: '/register'});
      window.open(href.href, '_blank')
    },
    toForgetPassword() {
      let href = this.$router.resolve({path: '/forgetPassword'});
      window.open(href.href, '_blank')
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
