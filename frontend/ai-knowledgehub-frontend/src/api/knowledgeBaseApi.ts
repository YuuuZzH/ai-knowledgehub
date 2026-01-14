import apiClient from './index'
import type { AxiosResponse } from 'axios'

// 知识库相关API接口
export interface KnowledgeBase {
  id: number
  name: string
  description: string
  documentCount: number
  createdAt: string
  isPublic: boolean
}

export interface KnowledgeBaseCreateRequest {
  name: string
  description: string
  isPublic: boolean
}

export interface KnowledgeBaseUpdateRequest {
  id: number
  name: string
  description: string
  isPublic: boolean
}

// 获取知识库列表
export const getKnowledgeBases = (): Promise<AxiosResponse<KnowledgeBase[]>> => {
  return apiClient.get('/knowledge-bases')
}

// 获取单个知识库
export const getKnowledgeBase = (id: number): Promise<AxiosResponse<KnowledgeBase>> => {
  return apiClient.get(`/knowledge-bases/${id}`)
}

// 创建知识库
export const createKnowledgeBase = (data: KnowledgeBaseCreateRequest): Promise<AxiosResponse<KnowledgeBase>> => {
  return apiClient.post('/knowledge-bases', data)
}

// 更新知识库
export const updateKnowledgeBase = (id: number, data: KnowledgeBaseUpdateRequest): Promise<AxiosResponse<KnowledgeBase>> => {
  return apiClient.put(`/knowledge-bases/${id}`, data)
}

// 删除知识库
export const deleteKnowledgeBase = (id: number): Promise<AxiosResponse<void>> => {
  return apiClient.delete(`/knowledge-bases/${id}`)
}