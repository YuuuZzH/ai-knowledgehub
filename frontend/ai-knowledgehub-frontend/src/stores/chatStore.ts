import { ref } from 'vue'
import { defineStore } from 'pinia'
import { chatApi } from '@/api/api'
import type { ChatMessage, ChatRequest } from '@/api/chatApi'

export const useChatStore = defineStore('chat', () => {
  // 聊天状态
  const messages = ref<ChatMessage[]>([])
  const sessionId = ref<string | null>(null)
  const loading = ref(false)
  const currentKnowledgeBaseId = ref<number | null>(null)

  // 开始新的聊天会话
  const startNewSession = async (knowledgeBaseId?: number) => {
    try {
      const response = await chatApi.startNewChat(knowledgeBaseId)
      sessionId.value = response.data.sessionId
      currentKnowledgeBaseId.value = knowledgeBaseId || null
      messages.value = []
      return { success: true, sessionId: response.data.sessionId }
    } catch (error) {
      console.error('Failed to start new session:', error)
      return { success: false, error }
    }
  }

  // 发送消息
  const sendMessage = async (message: string) => {
    if (!message.trim()) return { success: false, error: 'Message cannot be empty' }

    // 添加用户消息到列表
    const userMessage: ChatMessage = {
      id: Date.now(),
      content: message,
      role: 'user',
      timestamp: new Date().toISOString()
    }
    messages.value.push(userMessage)

    loading.value = true
    try {
      const request: ChatRequest = {
        message,
        knowledgeBaseId: currentKnowledgeBaseId.value || undefined,
        sessionId: sessionId.value || undefined
      }

      const response = await chatApi.sendChatMessage(request)
      
      // 添加AI回复到列表
      const aiMessage: ChatMessage = {
        id: Date.now() + 1,
        content: response.data.content,
        role: 'assistant',
        timestamp: new Date().toISOString(),
        knowledgeBaseId: currentKnowledgeBaseId.value || undefined
      }
      messages.value.push(aiMessage)

      // 更新session ID（如果它是新的）
      if (response.data.sessionId && !sessionId.value) {
        sessionId.value = response.data.sessionId
      }

      return { success: true, data: response.data }
    } catch (error) {
      console.error('Failed to send message:', error)
      // 移除刚才添加的用户消息，因为发送失败了
      messages.value.pop()
      return { success: false, error }
    } finally {
      loading.value = false
    }
  }

  // 获取聊天历史
  const loadChatHistory = async (session: string) => {
    try {
      const response = await chatApi.getChatHistory(session)
      messages.value = response.data
      sessionId.value = session
      return { success: true, data: response.data }
    } catch (error) {
      console.error('Failed to load chat history:', error)
      return { success: false, error }
    }
  }

  // 清空当前聊天
  const clearChat = () => {
    messages.value = []
    sessionId.value = null
  }

  // 设置当前知识库
  const setCurrentKnowledgeBase = (knowledgeBaseId: number | null) => {
    currentKnowledgeBaseId.value = knowledgeBaseId
  }

  return {
    messages,
    sessionId,
    loading,
    currentKnowledgeBaseId,
    startNewSession,
    sendMessage,
    loadChatHistory,
    clearChat,
    setCurrentKnowledgeBase
  }
})