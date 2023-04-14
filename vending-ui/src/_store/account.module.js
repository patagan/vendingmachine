import { userService } from "../_services";
import router from "../router";



export const account = {
  state() {
    
    const user = JSON.parse(localStorage.getItem("user"));
    return user
      ? { loggedIn: true , user }
      : { loggedIn: false, registering: false, user: null };
  },
  mutations: {
    loginRequest(state, user) {
      state.status = { loggingIn: true };
      state.user = user;
    },
    loginSuccess(state, user) {
      state.status = { loggedIn: true };
      state.user = user;
    },
    loginFailure(state) {
      state.status = {};
      state.user = null;
    },
    logout(state) {
      state.status = {};
      state.user = null;
    },
    registerRequest(state) {
      state.status = { registering: true };
    },
    registerSuccess(state) {
      state.status = {};
    },
    registerFailure(state) {
      state.status = {};
    },
    topUpSuccess(state, deposit) {
      state.user.deposit = deposit;
    }
  },
  actions: {
    login({ dispatch, commit }, { username, password }) {
  
      userService.login(username, password).then(
        user => {
          commit("loginSuccess", user);
          router.push("/");
        },
        error => {
          commit("loginFailure", error);
          dispatch("error", error, { root: true });
        }
      );
    },
    logout({ commit }) {
      userService.logout();
      router.push("/login");
      commit("logout");
    },
    getUser({ dispatch, commit }, userId) {
      userService.getUser(userId).then(
        user => {
          commit("loginSuccess", user);
          router.push("/");
        },
        error => {
          commit("loginFailure", error);
          dispatch("error", error, { root: true });
        }
      );
    },
    register({ dispatch, commit }, user) {
      commit("registerRequest", user);
  
      userService.register(user).then(
        user => {
          commit("registerSuccess", user);
          router.push("/login");
          setTimeout(() => {
            // display success message after route change completes
            dispatch("success", "Registration successful", { root: true });
          });
        },
        error => {
          commit("registerFailure", error);
          dispatch("error", error, { root: true });
        }
      );
    },
    topUp({ commit, dispatch }, budget) {
      userService.topUp(budget).then(
        deposit => {
          commit("topUpSuccess", deposit);
          setTimeout(() => {
            // display success message after route change completes
            dispatch("success", "TopUp successful", { root: true });
          });
        },
        error => {
          dispatch("error", error, { root: true });
        }
      );
    },
    resetDeposit({ commit, dispatch }) {
      userService.resetDeposit().then(
        deposit => {
          commit("topUpSuccess", deposit);
          setTimeout(() => {
            // display success message after route change completes
            dispatch("success", "Reset successful", { root: true });
          });
        },
        error => {
          dispatch("error", error, { root: true });
        }
      );
    },
    buyProduct({ commit, dispatch }, buyInput) {
      userService.buyProduct(buyInput).then(
        response => {
          commit("topUpSuccess", response.depositLeft);
          setTimeout(() => {
            dispatch("getProducts")
            // display success message after route change completes
            dispatch("success", "You habe spent " + response.moneySpent + " € in " + response.productName + ", here is your change coins: " + response.changeMoney, { root: true });
          });
        },
        error => {
          dispatch("error", error, { root: true });
        }
      );
    },
  },
  getters: {
    getStatus: state => state.status,
    getUser: state => state.user
  }
};