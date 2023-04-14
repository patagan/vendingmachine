import { productService } from "../_services";

export const product = {
  state() {
    return {
        products: [],
      }
  },
  actions: {
    getProducts({ dispatch, commit }) {
      productService.getProducts().then(
        products => {
          commit("getProductsSuccess", products);
        },
        error => {
          dispatch("error", error, { root: true });
        }
      );
    },
    addProduct({ commit, dispatch }, product) {
        productService.addProduct(product).then(
            product => {
                commit("addProductSuccess", product);
              },
              error => {
                dispatch("error", error, { root: true });
              }
        )
    },
    updateProduct({ commit,dispatch }, product) {
        productService.updateProduct(product).then(
            product => {
                commit("updateProductSuccess", product);
              },
              error => {
                dispatch("error", error, { root: true });
              }
        )
    },
    deleteProduct({ commit,dispatch }, productId) {
        productService.deleteProduct(productId).then(
            productId => {
                commit("removeProductSuccess", productId);
              },
              error => {
                dispatch("error", error, { root: true });
              }
        )
    },
  },
  mutations: {
    getProductsSuccess(state, products) {
      state.products = products;
    },
    addProductSuccess(state, product) {
      state.products.push(product);
    },
    updateProductSuccess(state, product) {
      for (let i = state.products.length - 1; i >= 0; i--) {
        if (state.products[i].id === product.id) {
          state.products[i] = product
        }
      }
    },
    removeProductSuccess(state, productId) {
      for (let i = state.products.length - 1; i >= 0; i--) {
        if (state.products[i].id === productId) {
          state.products.splice(i, 1);
        }
      }
    },
    registerRequest(state) {
      state.status = { registering: true };
    },
    registerSuccess(state) {
      state.status = {};
    },
    registerFailure(state) {
      state.status = {};
    }
  }
};