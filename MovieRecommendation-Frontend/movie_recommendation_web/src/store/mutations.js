import * as types from "./mutations_types";

export default {
  [types.SET_IS_AUTHENTICATED](state, data) {
    state.isAuthenticated = data;
  },
  [types.SET_CURRENT_USER](state, data) {
    state.currentUser = data;
  }
}
