import apiClient from './index'
import type { AxiosResponse } from 'axios'

// 问答相关API接口
export interface ChatMessage {
  id: number
  content: string
  role: 'user' | 'assistant'
  timestamp: string
  knowledgeBaseId?: number
}

export interface ChatRequest {
  message: string
  knowledgeBaseId?: number
  sessionId?: string
}

export interface ChatResponse {
  id: string
  content: string
  sessionId: string
  sources?: string[]
  timestamp: string
}

// 发送消息并获取回答
export const sendChatMessage = (data: ChatRequest): Promise<AxiosResponse<ChatResponse>> => {
  return apiClient.post('/chat', data)
}

// 开始新的聊天会话
export const startNewChat = (knowledgeBaseId?: number): Promise<AxiosResponse<{ sessionId: string }>> => {
  return apiClient.post('/chat/start', { knowledgeBaseId })
}

// 获取聊天历史
export const getChatHistory = (sessionId: string): Promise<AxiosResponse<ChatMessage[]>> => {
  return apiClient.get(`/chat/history/${sessionId}`)
}