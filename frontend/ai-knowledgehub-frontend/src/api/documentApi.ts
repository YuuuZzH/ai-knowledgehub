import apiClient from './index'
import type { AxiosResponse } from 'axios'

// 文档相关API接口
export interface Document {
  id: number
  filename: string
  originalName: string
  size: number
  uploadDate: string
  knowledgeBaseId: number
  status: 'uploading' | 'processing' | 'completed' | 'failed'
}

export interface UploadDocumentRequest {
  file: File
  knowledgeBaseId: number
}

// 获取文档列表
export const getDocuments = (knowledgeBaseId?: number): Promise<AxiosResponse<Document[]>> => {
  const params: Record<string, any> = {}
  if (knowledgeBaseId) {
    params.knowledgeBaseId = knowledgeBaseId
  }
  return apiClient.get('/documents', { params })
}

// 上传文档
export const uploadDocument = (data: FormData): Promise<AxiosResponse<Document>> => {
  return apiClient.post('/documents/upload', data, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

// 删除文档
export const deleteDocument = (id: number): Promise<AxiosResponse<void>> => {
  return apiClient.delete(`/documents/${id}`)
}

// 获取文档详情
export const getDocument = (id: number): Promise<AxiosResponse<Document>> => {
  return apiClient.get(`/documents/${id}`)
}