import apiClient from './index'
import type { AxiosResponse } from 'axios'

// 用户相关API接口
export interface User {
  id: number
  username: string
  email: string
  role: string
  avatar?: string
  lastLogin?: string
  joinDate: string
}

export interface LoginRequest {
  username: string
  password: string
}

export interface LoginResponse {
  access_token: string
  token_type: string
  expires_in: number
  user: User
}

export interface RegisterRequest {
  username: string
  email: string
  password: string
}

export interface UpdateProfileRequest {
  username?: string
  email?: string
  avatar?: string
}

// 用户登录
export const login = (data: LoginRequest): Promise<AxiosResponse<LoginResponse>> => {
  return apiClient.post('/auth/login', data)
}

// 用户注册
export const register = (data: RegisterRequest): Promise<AxiosResponse<User>> => {
  return apiClient.post('/auth/register', data)
}

// 获取当前用户信息
export const getCurrentUser = (): Promise<AxiosResponse<User>> => {
  return apiClient.get('/users/me')
}

// 更新用户信息
export const updateUserProfile = (data: UpdateProfileRequest): Promise<AxiosResponse<User>> => {
  return apiClient.put('/users/me', data)
}

// 修改密码
export const changePassword = (oldPassword: string, newPassword: string): Promise<AxiosResponse<void>> => {
  return apiClient.post('/users/change-password', {
    oldPassword,
    newPassword
  })
}