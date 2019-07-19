// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import Vuex from 'vuex'
import App from './App'
import router from './router'
import store from './store'
import i18n from './lang' // Internationalization
import { global } from '@/global/global'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import VueUtils from '@/utils/vueUtils'
import md5 from 'js-md5'
import EventBusUtils from '@/utils/eventBusUtils'

import '@/genRouter' // permission control
Vue.use(Vuex)
Vue.use(ElementUI, {
  size: 'medium', // set element-ui default size
  i18n: (key, value) => i18n.t(key, value)
})

Vue.prototype.VueUtils = VueUtils;
Vue.prototype.$md5 = md5;
Vue.prototype.EventBusUtils = EventBusUtils;

// 加载用户主题
if (localStorage.getItem('themeValue')) {
  global.changeTheme(localStorage.getItem('themeValue'))
} else {
  global.changeTheme('default')
}
Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  i18n,
  components: { App },
  template: '<App/>'
})
