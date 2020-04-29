import Vue from 'vue'
import App from './App.vue'
import BootstrapVue from 'bootstrap-vue/dist/bootstrap-vue.esm';
import 'bootstrap/dist/css/bootstrap.min.css'
import router from './router'
import page from 'v-page';

Vue.config.productionTip = false
Vue.use(BootstrapVue);
Vue.use(page);

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
