// API 基础配置
import axios, { type AxiosResponse, type InternalAxiosRequestConfig } from 'axios'

// 创建 axios 实例
const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api', // 默认后端API地址
  timeout: 30000, // 请求超时时间
  headers: {
    'Content-Type': 'application/json',
  },
})

// 请求拦截器
apiClient.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    // 在发送请求之前做些什么，比如添加认证token
    const token = localStorage.getItem('access_token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error: any) => {
    // 对请求错误做些什么
    return Promise.reject(error)
  }
)

// 响应拦截器
apiClient.interceptors.response.use(
  (response: AxiosResponse) => {
    // 对响应数据做点什么
    return response.data
  },
  (error: any) => {
    // 对响应错误做点什么
    if (error.response?.status === 401) {
      // 未授权，跳转到登录页
      localStorage.removeItem('access_token')
      window.location.href = '/login'
    }
    return Promise.reject(error)
  }
)

export default apiClient