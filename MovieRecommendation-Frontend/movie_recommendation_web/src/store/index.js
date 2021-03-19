import Vue from 'vue'
import Vuex from 'vuex'

import mutations from './mutations'

Vue.use(Vuex)

const store = new Vuex.Store({

  state: {
    // 用户登录信息
    isAuthenticated: false,
    currentUser: null,
  },
  getters: {},
  mutations,
  actions: {},
  modules: {}
})

export default store
