import './assets/main.scss'
import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import router from '@/router'
import {createPinia} from 'pinia'
import { createPersistedState } from 'pinia-persistedstate-plugin'
import locale from 'element-plus/dist/locale/zh-cn.js'
import * as ELIcons from "@element-plus/icons-vue";
const app = createApp(App)
// 小图标引用
for (const iconName in ELIcons) {
    // 注册组件
    app.component(iconName, ELIcons[iconName]);
  }
const pinia = createPinia()
const persist = createPersistedState()
pinia.use(persist)
app.use(router)
app.use(pinia)
app.use(ElementPlus,{locale})
app.mount('#app')
