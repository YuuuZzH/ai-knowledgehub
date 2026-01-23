import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

// Element Plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import App from './App.vue'
import router from './router'
import { useUserStore } from './stores'

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(ElementPlus)

// 在应用启动时检查认证状态
const userStore = useUserStore()
userStore.checkAuthStatus()

app.mount('#app')
