
import { createStore } from 'vuex';

import { alert } from "./alert.module";
import { account } from "./account.module";
import { product } from "./product.module";

const store = createStore({
  modules: {
    alert: alert,
    account: account,
    product: product
  }
});

export default store;