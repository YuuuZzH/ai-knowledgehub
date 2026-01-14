import { ref } from 'vue'
import { defineStore } from 'pinia'
import { userApi } from '@/api/api'
import type { User } from '@/api/userApi'

export const useUserStore = defineStore('user', () => {
  // 用户状态
  const user = ref<User | null>(null)
  const isAuthenticated = ref(false)
  const token = ref<string | null>(localStorage.getItem('access_token'))

  // 登录
  const login = async (username: string, password: string) => {
    try {
      const response = await userApi.login({ username, password })
      const { access_token, user: userData } = response.data
      
      // 保存token到本地存储和状态
      localStorage.setItem('access_token', access_token)
      token.value = access_token
      user.value = userData
      isAuthenticated.value = true
      
      return { success: true, data: response.data }
    } catch (error) {
      console.error('Login failed:', error)
      return { success: false, error }
    }
  }

  // 注册
  const register = async (username: string, email: string, password: string) => {
    try {
      const response = await userApi.register({ username, email, password })
      return { success: true, data: response.data }
    } catch (error) {
      console.error('Register failed:', error)
      return { success: false, error }
    }
  }

  // 获取当前用户信息
  const fetchCurrentUser = async () => {
    if (!token.value) {
      return { success: false, error: 'No token available' }
    }

    try {
      const response = await userApi.getCurrentUser()
      user.value = response.data
      isAuthenticated.value = true
      return { success: true, data: response.data }
    } catch (error) {
      console.error('Failed to fetch user:', error)
      logout()
      return { success: false, error }
    }
  }

  // 登出
  const logout = () => {
    localStorage.removeItem('access_token')
    token.value = null
    user.value = null
    isAuthenticated.value = false
  }

  // 更新用户信息
  const updateProfile = async (userData: Partial<User>) => {
    try {
      const response = await userApi.updateUserProfile(userData)
      user.value = { ...user.value!, ...response.data }
      return { success: true, data: response.data }
    } catch (error) {
      console.error('Failed to update profile:', error)
      return { success: false, error }
    }
  }

  return {
    user,
    isAuthenticated,
    token,
    login,
    register,
    fetchCurrentUser,
    logout,
    updateProfile
  }
})