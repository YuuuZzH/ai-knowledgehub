import { ref } from 'vue'
import { defineStore } from 'pinia'
import { knowledgeBaseApi } from '@/api/api'
import type { KnowledgeBase, KnowledgeBaseCreateRequest, KnowledgeBaseUpdateRequest } from '@/api/knowledgeBaseApi'

export const useKnowledgeBaseStore = defineStore('knowledgeBase', () => {
  // 知识库状态
  const knowledgeBases = ref<KnowledgeBase[]>([])
  const currentKnowledgeBase = ref<KnowledgeBase | null>(null)
  const loading = ref(false)

  // 获取知识库列表
  const fetchKnowledgeBases = async () => {
    loading.value = true
    try {
      const response = await knowledgeBaseApi.getKnowledgeBases()
      knowledgeBases.value = response.data
      return { success: true, data: response.data }
    } catch (error) {
      console.error('Failed to fetch knowledge bases:', error)
      return { success: false, error }
    } finally {
      loading.value = false
    }
  }

  // 获取单个知识库
  const fetchKnowledgeBase = async (id: number) => {
    loading.value = true
    try {
      const response = await knowledgeBaseApi.getKnowledgeBase(id)
      currentKnowledgeBase.value = response.data
      return { success: true, data: response.data }
    } catch (error) {
      console.error(`Failed to fetch knowledge base ${id}:`, error)
      return { success: false, error }
    } finally {
      loading.value = false
    }
  }

  // 创建知识库
  const createKnowledgeBase = async (data: KnowledgeBaseCreateRequest) => {
    loading.value = true
    try {
      const response = await knowledgeBaseApi.createKnowledgeBase(data)
      knowledgeBases.value.push(response.data)
      return { success: true, data: response.data }
    } catch (error) {
      console.error('Failed to create knowledge base:', error)
      return { success: false, error }
    } finally {
      loading.value = false
    }
  }

  // 更新知识库
  const updateKnowledgeBase = async (id: number, data: KnowledgeBaseUpdateRequest) => {
    loading.value = true
    try {
      const response = await knowledgeBaseApi.updateKnowledgeBase(id, data)
      const index = knowledgeBases.value.findIndex(kb => kb.id === id)
      if (index !== -1) {
        knowledgeBases.value[index] = response.data
      }
      // 如果是当前选中的知识库，也要更新
      if (currentKnowledgeBase.value?.id === id) {
        currentKnowledgeBase.value = response.data
      }
      return { success: true, data: response.data }
    } catch (error) {
      console.error(`Failed to update knowledge base ${id}:`, error)
      return { success: false, error }
    } finally {
      loading.value = false
    }
  }

  // 删除知识库
  const deleteKnowledgeBase = async (id: number) => {
    loading.value = true
    try {
      await knowledgeBaseApi.deleteKnowledgeBase(id)
      knowledgeBases.value = knowledgeBases.value.filter(kb => kb.id !== id)
      // 如果删除的是当前选中的知识库，则清空
      if (currentKnowledgeBase.value?.id === id) {
        currentKnowledgeBase.value = null
      }
      return { success: true }
    } catch (error) {
      console.error(`Failed to delete knowledge base ${id}:`, error)
      return { success: false, error }
    } finally {
      loading.value = false
    }
  }

  // 设置当前知识库
  const setCurrentKnowledgeBase = (kb: KnowledgeBase | null) => {
    currentKnowledgeBase.value = kb
  }

  return {
    knowledgeBases,
    currentKnowledgeBase,
    loading,
    fetchKnowledgeBases,
    fetchKnowledgeBase,
    createKnowledgeBase,
    updateKnowledgeBase,
    deleteKnowledgeBase,
    setCurrentKnowledgeBase
  }
})