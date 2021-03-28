import Vue from 'vue'
import Vuex from 'vuex'

import mutations from './mutations'

Vue.use(Vuex)

const store = new Vuex.Store({

  state: {
    // 用户登录信息
    isAuthenticated: false,
    currentUser: null,

    // 电影分类标签
    movieTags: ['爱情', '喜剧', '冒险', '科幻', '黑色电影', '脱口秀', '奇幻', '历史', '戏曲', '悬疑', '动作', '歌舞', '古装',
      '同性', '西部', '儿童', '真人秀', '战争', '情色', '传记', '动画', '惊悚', '犯罪', '运动', '灾难', '荒诞', '音乐', '家庭',
      '恐怖', '武侠', '剧情'],
    // 电影地区
    productionCountryArea: ['中国大陆', '中国香港', '中国台湾', '美国', '韩国', '英国', '法国', '意大利', '德国', '西班牙',
      '印度', '泰国', '俄罗斯', '伊朗', '加拿大', '澳大利亚', '爱尔兰', '瑞典', '巴西', '丹麦'],
    //语言
    language: ['汉语普通话', '粤语', '英语', '法语', '日语', '德语', '西班牙语', '波兰语', '希腊语', '丹麦语',
      '葡萄牙语', '印度语', '泰语', '手语']
  },
  getters: {},
  mutations,
  actions: {},
  modules: {}
})

export default store
