/**
 * main.js
 *
 * Bootstraps Vuetify and other plugins then mounts the App`
 */

// Components
import { createApp } from 'vue';
import App from './App.vue'
import router from "./router";
import store from './_store';
import vuetify from "./plugins/vuetify";

const app = createApp(App);
app.use(store);
app.use(router);
app.use(vuetify);
app.mount('#app');

