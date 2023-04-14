export const alert = {
  state() {
    return {
      type: null,
      message: null
    }
  },
  mutations: {
    success(state, message) {
      state.type = "success";
      state.message = message;
    },
    error(state, message) {
      state.type = "error";
      state.message = message;
    },
    clear(state) {
      state.type = null;
      state.message = null;
    }
  },
  actions: {
    success({ commit, dispatch }, message) {
      commit("success", message);
      setTimeout(() => {
        dispatch('clear')
      }, 3000)
    },
    error({ commit, dispatch }, message) {
      commit("error", message);
      setTimeout(() => {
        dispatch('clear')
      }, 3000)
    },
    clear({ commit }, message) {
      commit("success", message);
    }
  },
  getters: {
    getType: state => state.type,
    getMessage: state => state.message
  }
};

  