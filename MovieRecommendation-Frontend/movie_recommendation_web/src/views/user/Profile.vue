<template>
  <div>
    <top-bar></top-bar>
    <div class="content-root-container">
      <div>

        <el-row>
          <el-col :span="16">
            <div class="title-style">基本信息</div>
          </el-col>
          <el-col :span="8" style="padding-left: 50px">
            <div class="title-style">我喜欢的电影分类</div>
          </el-col>
        </el-row>

        <el-row style="margin-top: 25px">
          <el-col :span="4" style="text-align: center; margin-top: 150px">
            <el-avatar :src="userInfo.userHeadPortraitUrl" :size="150"/>
          </el-col>
          <el-col :span="12">
            <el-form ref="userInfo" :model="userInfo" :rules="rules" label-width="100px">
              <el-form-item label="昵称" prop="userName">
                <el-input v-model="userInfo.userName"></el-input>
              </el-form-item>
              <el-form-item label="账号">
                {{ userInfo.account }}
              </el-form-item>
              <el-form-item label="密码">
                <span style="color: #5699dd; cursor: pointer" @click="toChangePasswordPage">修改密码</span>
              </el-form-item>
              <el-form-item label="邮箱" prop="email">
                <el-input v-model="userInfo.email"></el-input>
              </el-form-item>
              <el-form-item label="电话">
                <el-input v-model="userInfo.phone"></el-input>
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
                <el-button type="primary" icon="el-icon-edit" @click="changeInfo">修改信息</el-button>
              </el-form-item>
            </el-form>
          </el-col>
          <el-col :span="8" style="padding-left: 50px">
            <div>
              <v-chip
                style="margin-right: 5px"
                v-for="(item, index) in tagPrefer"
                :key="item.tagId"
                :color="chipColorList[index % 5]">
                {{ item.tagName }}
              </v-chip>
            </div>
            <div style="text-align: center">
              <el-button plain type="warning" icon="el-icon-edit" @click="showTagBoard">修 改</el-button>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
    <movie-tag v-if="isShowTagBoard" :dialog-visible="true" :tag-list="this.tagList"></movie-tag>
  </div>
</template>

<script>
import TopBar from "@/components/topbar/TopBar";
import * as userApi from "@/api/user/userApi"
import * as tagPreferApi from "@/api/tag/tagPreferApi"
import MovieTag from "@/components/tag/MovieTag";
import * as tagApi from "@/api/tag/tagApi";

export default {
  name: "Profile",
  components: {MovieTag, TopBar},
  data() {
    return {
      jobs: this.$store.state.job,
      // userInfo: JSON.parse(sessionStorage.getItem('currentUser')),
      userInfo: {
        userId: 0,
        userName: '',
        userUniqueName: '',
        userHeadPortraitUrl: '',
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
        email: [
          {required: true, message: '请输入邮箱地址', trigger: 'blur'},
          {type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change']}
        ],
      },
      tagPrefer: [],
      tagList: [],
      isShowTagBoard: false,
      chipColorList: ["#fc9d99", "#84af9b", "#5699dd", "#fc9d99", "#c8c7a8"],
    }
  },
  created() {
    this.userInfo = JSON.parse(sessionStorage.getItem('currentUser'))
    tagPreferApi.queryUserTagPreferDetailById(this.userInfo.userId)
      .then(res => {
        this.tagPrefer = res.data.data
      })
    tagApi.queryAllTags()
      .then(res => {
        this.tagList = res.data.data
      })
  },
  methods: {
    toChangePasswordPage() {
      let href = this.$router.resolve({path: '/changePassword'});
      window.open(href.href, '_blank')
    },
    changeInfo() {
      userApi.changeInfo(this.userInfo)
        .then(res => {
          if (res.data.success) {
            this.$message.success("修改成功！")
            sessionStorage.setItem("currentUser", JSON.stringify(this.userInfo))
          }
        }).catch(() => {
        this.$message.error("修改失败！")
      })
    },
    showTagBoard() {
      this.isShowTagBoard = !this.isShowTagBoard
    }
  }
}
</script>

<style scoped>
.content-root-container {
  margin-top: 2vh;
  margin-left: 2vw;
  margin-right: 2vw;
}

.title-style {
  font-size: 25px;
  color: #5699dd;
  font-weight: bold;
}

</style>
