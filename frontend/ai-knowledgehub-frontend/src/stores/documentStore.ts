import { ref } from 'vue'
import { defineStore } from 'pinia'
import { documentApi } from '@/api/api'
import type { Document } from '@/api/documentApi'

export const useDocumentStore = defineStore('document', () => {
  // 文档状态
  const documents = ref<Document[]>([])
  const currentDocument = ref<Document | null>(null)
  const loading = ref(false)

  // 获取文档列表
  const fetchDocuments = async (knowledgeBaseId?: number) => {
    loading.value = true
    try {
      const response = await documentApi.getDocuments(knowledgeBaseId)
      documents.value = response.data
      return { success: true, data: response.data }
    } catch (error) {
      console.error('Failed to fetch documents:', error)
      return { success: false, error }
    } finally {
      loading.value = false
    }
  }

  // 获取单个文档
  const fetchDocument = async (id: number) => {
    loading.value = true
    try {
      const response = await documentApi.getDocument(id)
      currentDocument.value = response.data
      return { success: true, data: response.data }
    } catch (error) {
      console.error(`Failed to fetch document ${id}:`, error)
      return { success: false, error }
    } finally {
      loading.value = false
    }
  }

  // 上传文档
  const uploadDocument = async (file: File, knowledgeBaseId: number) => {
    loading.value = true
    try {
      const formData = new FormData()
      formData.append('file', file)
      formData.append('knowledgeBaseId', knowledgeBaseId.toString())

      const response = await documentApi.uploadDocument(formData)
      documents.value.unshift(response.data)
      return { success: true, data: response.data }
    } catch (error) {
      console.error('Failed to upload document:', error)
      return { success: false, error }
    } finally {
      loading.value = false
    }
  }

  // 删除文档
  const deleteDocument = async (id: number) => {
    loading.value = true
    try {
      await documentApi.deleteDocument(id)
      documents.value = documents.value.filter(doc => doc.id !== id)
      // 如果删除的是当前选中的文档，则清空
      if (currentDocument.value?.id === id) {
        currentDocument.value = null
      }
      return { success: true }
    } catch (error) {
      console.error(`Failed to delete document ${id}:`, error)
      return { success: false, error }
    } finally {
      loading.value = false
    }
  }

  // 设置当前文档
  const setCurrentDocument = (doc: Document | null) => {
    currentDocument.value = doc
  }

  return {
    documents,
    currentDocument,
    loading,
    fetchDocuments,
    fetchDocument,
    uploadDocument,
    deleteDocument,
    setCurrentDocument
  }
})