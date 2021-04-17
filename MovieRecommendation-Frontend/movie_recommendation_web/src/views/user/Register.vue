<template>
  <div class="root-container">
    <div style="margin-left: 20%; margin-right: 20%">
      <el-steps :active="active" finish-status="success" simple>
        <el-step title="填写信息"></el-step>
        <el-step title="人机验证"></el-step>
        <el-step title="喜好标签"></el-step>
        <el-step title="完成注册"></el-step>
      </el-steps>
    </div>
    <div style="margin-left: 25%; margin-right: 25%; margin-top: 30px; padding-bottom: 100px">
      <div v-if="isShowInfo">
        <el-form ref="userInfo" :model="userInfo" :rules="rules" label-width="100px">
          <el-form-item label="昵称" prop="userName">
            <el-input v-model="userInfo.userName"></el-input>
          </el-form-item>
          <el-form-item label="联系电话" prop="phone">
            <el-input v-model="userInfo.phone" placeholder="该项作为您的账号"></el-input>
          </el-form-item>
          <el-form-item label="登录密码" prop="password">
            <el-input v-model="userInfo.password" placeholder="请输入密码" show-password></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="userInfo.email"></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-radio-group v-model="userInfo.sex">
              <el-radio label="男"></el-radio>
              <el-radio label="女"></el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="生日">
            <el-date-picker type="date"
                            placeholder="选择日期"
                            v-model="userInfo.birth"
                            value-format="yyyy-MM-dd"
                            format="yyyy 年 MM 月 dd 日"
                            style="width: 100%;">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="职业">
            <el-select v-model="userInfo.profession" placeholder="请选择职业">
              <el-option v-for="(item,index) in jobs" :key="index" :label="item" :value="item"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-edit" @click="infoAndNextStep">下一步</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div v-if="isShowVerify">
        <el-form ref="verifyForm" :model="verifyForm" label-width="100px" :rules="rules">
          <el-form-item label="电话">
            <el-input v-model="userInfo.phone">
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
            <el-input v-model="verifyForm.verifyCode"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-edit" @click="verifyAndNextStep">校验</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div v-if="isShowTag">
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
        <el-button type="primary" icon="el-icon-edit" @click="tagAndNextStep" style="margin-top: 20px; margin-left: 25%">下一步</el-button>
      </div>
      <div v-if="isShowFinish" style="text-align: center">
        <el-button type="success" @click="submitInfo">保存并返回主界面登录</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import ImgConstants from "@/common/constant/ImgConstants";
import * as verificationCodeApi from "@/api/verification/verificationCodeApi";
import * as tagApi from "@/api/tag/tagApi";
import * as userLoginRegisterApi from "@/api/user/userLoginRegisterApi"
import * as userTagPreferApi from "@/api/tag/tagPreferApi";
import transfer from "@/common/utils/transfer";

export default {
  name: "Register",
  data() {
    return {
      active: 0,
      isShowInfo: true,
      isShowVerify: false,
      btnContent: '发送验证码',
      totalTime: 30,
      canClick: true,
      isShowTag: false,
      tagList: [],
      chipColorList: ["item-color1 ", "item-color2 ", "item-color3 ", "item-color4 ", "item-color5 "],
      tagTemp: [],
      isShowFinish: false,

      userInfo: {
        userId: 0,
        userName: '',
        userUniqueName: '',
        userHeadPortraitUrl: ImgConstants.DEFAULT_AVATAR_URL,
        userUrl: '',
        account: '',
        password: '',
        email: '',
        phone: '',
        sex: '',
        birth: '',
        age: 0,
        profession: ''
      },
      rules: {
        userName: [
          {required: true, message: '请输入昵称', trigger: 'blur'}
        ],
        phone: [
          {required: true, message: '请输入电话号码', trigger: 'blur'}
        ],
        email: [
          {type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change']}
        ],
        verifyCode: [
          {required: true, message: '请输入验证码', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 8, max: 20, message: '最少8个字符', trigger: 'blur'}
        ]
      },
      jobs: this.$store.state.job,
      verifyForm: {
        verifyCode: ''
      }

    }
  },
  methods: {
    infoAndNextStep() {
      if (this.userInfo.phone === '' || this.userInfo.userName === '') {
        this.$message.error("请填入必填项（带*为必填项）!")
      } else {
        this.isShowInfo = false
        this.isShowVerify = true
        this.active = 1
      }
    },
    countDown() {
      if (!this.canClick) return
      this.canClick = false
      this.btnContent = this.totalTime + 's后重新发送'
      // 获取验证码
      verificationCodeApi.getCode(this.userInfo.phone)
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
    verifyAndNextStep() {
      if (this.verifyForm.verifyCode === '') {
        this.$message.error("请填写验证码！")
      } else {
        //校验
        verificationCodeApi.checkCode(this.userInfo.phone, this.verifyForm.verifyCode)
          .then(res => {
            if (res.data.success) {
              this.$message.success("校验成功！")
              this.isShowVerify = false
              this.isShowTag = true
              this.active = 2
            } else {
              this.$message.error("验证码错误！请检查后重试！")
            }
          })
          .catch(() => {
            this.$message.error("请求超时！")
          })
      }
    },
    addToUserPrefer(tagId) {
      let index = this.tagTemp.indexOf(tagId)
      if (index !== -1) {
        this.tagTemp.splice(index, 1)
      } else {
        this.tagTemp.push(tagId)
      }
    },
    tagAndNextStep() {
      this.isShowTag = false
      this.isShowFinish = true
      this.active = 3
    },
    submitInfo() {
      userLoginRegisterApi.register(this.userInfo)
      .then(res => {
        if (res.data.success) {
          let userId = res.data.data.userId
          let tagList = transfer.numberListToString(this.tagTemp, ",");
          userTagPreferApi.addUserTagPrefer({userId: userId, tagList: tagList})
          .then(res => {
            if (res.data.success) {
              this.$message.success("注册成功！")
              this.$router.replace('/login');
            }else {
              this.$message.error("注册失败！")
              this.$router.replace('/register');
            }
          })
        }
      })
    }
  },
  created() {
    tagApi.queryAllTags()
      .then(res => {
        this.tagList = res.data.data
      })
  }
}
</script>

<style scoped>
.root-container {
  margin-top: 15vh;
}

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
</style>
