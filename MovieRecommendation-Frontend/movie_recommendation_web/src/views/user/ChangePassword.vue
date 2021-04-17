<template>
  <div>
    <div class="content-container">
      <el-form :model="changePwdForm" ref="changePwdForm" :rules="rules" label-width="100px" style="width: 40%">
        <el-form-item label="手机号码">
          <el-input v-model="changePwdForm.account">
            <el-button type="primary"
                       round
                       @click="countDown"
                       slot="append"
                       :class="{disabled: !this.canClick}">
              {{ btnContent }}
            </el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="验证码" prop="verifyCode">
          <el-input v-model="changePwdForm.verifyCode"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model="changePwdForm.newPassword" show-password></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="changePwdForm.confirmPassword" show-password></el-input>
        </el-form-item>
      </el-form>
      <el-button type="primary" :loading="isLoading" @click="changePwd" style="margin-left: 18%">{{
          submitBtnContent
        }}
      </el-button>
    </div>
  </div>
</template>

<script>
import * as verificationCodeApi from "@/api/verification/verificationCodeApi"
import * as userApi from "@/api/user/userApi"

export default {
  name: "ChangePassword",
  data() {
    let validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.changePwdForm.newPassword) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      userInfo: JSON.parse(sessionStorage.getItem('currentUser')),
      btnContent: '发送验证码',
      totalTime: 30,
      canClick: true,
      changePwdForm: {
        account: '',
        verifyCode: '',
        newPassword: '',
        confirmPassword: ''
      },
      rules: {
        verifyCode: [
          {required: true, message: '请输入验证码', trigger: 'blur'}
        ],
        newPassword: [
          {required: true, message: '请输入新密码', trigger: 'blur'},
          {min: 8, max: 20, message: '最少8个字符', trigger: 'blur'}
        ],
        confirmPassword: [
          {required: true, validator: validatePass, trigger: 'blur'}
        ],
      },
      submitBtnContent: '保存并重新登陆',
      isLoading: false,
    }
  },
  created() {
    this.changePwdForm.account = this.userInfo.account
  },
  methods: {
    // 显示倒计时
    countDown() {
      if (!this.canClick) return
      this.canClick = false
      this.btnContent = this.totalTime + 's后重新发送'
      // 获取验证码
      verificationCodeApi.getCode(this.changePwdForm.account)
        .catch(() => {
          this.$message.error("发送验证码失败！")
        })
      let clock = window.setInterval(() => {
        this.totalTime--
        this.btnContent = this.totalTime + 's后重新发送'
        if (this.totalTime < 0) {
          window.clearInterval(clock)
          this.btnContent = '重新发送验证码'
          this.totalTime = 30
          this.canClick = true
        }
      }, 1000)
    },
    changePwd() {
      this.isLoading = true
      this.submitBtnContent = '提交数据中'
      userApi.changePwd(this.changePwdForm)
        .then(res => {
          if (res.data.success) {
            setTimeout(() => {
              this.isLoading = false
              this.submitBtnContent = '保存并重新登陆'
              sessionStorage.setItem("currentUser", JSON.stringify(res.data.data))
              this.$router.replace('/login')
              this.$message.success("密码修改成功！请重新登录！")
            }, 2000)

          } else {
            this.isLoading = false
            this.submitBtnContent = '保存并重新登录'
            this.$message.error("修改失败！请检查验证码获密码是否正确！")
          }
        }).catch(() => {
        this.isLoading = false
        this.submitBtnContent = '保存并重新登陆'
        this.$message.error("请求超时！")
      })
    }
  }
}
</script>

<style scoped>
.content-container {
  margin-top: 15vh;
  margin-left: 30%;
}

.disabled {
  background-color: #ddd;
  border-color: #ddd;
  color: #57a3f3;
  cursor: not-allowed;
}
</style>
