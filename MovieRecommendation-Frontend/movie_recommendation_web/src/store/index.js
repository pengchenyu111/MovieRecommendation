import {createStore} from 'vuex'
import mutations from './mutations'

const store = createStore({

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
