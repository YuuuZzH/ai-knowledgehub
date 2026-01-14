<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

defineOptions({
  name: 'DocumentsView'
})

interface Document {
  id: number
  filename: string
  originalName: string
  size: number
  uploadDate: string
  knowledgeBaseId: number
  status: 'uploading' | 'processing' | 'completed' | 'failed'
}

const documents = ref<Document[]>([
  {
    id: 1,
    filename: 'company_policy.pdf',
    originalName: '公司政策手册.pdf',
    size: 2456789,
    uploadDate: '2024-01-15',
    knowledgeBaseId: 1,
    status: 'completed'
  },
  {
    id: 2,
    filename: 'product_manual.docx',
    originalName: '产品使用手册.docx',
    size: 1234567,
    uploadDate: '2024-02-20',
    knowledgeBaseId: 2,
    status: 'completed'
  },
  {
    id: 3,
    filename: 'training_guide.pptx',
    originalName: '培训指南.pptx',
    size: 3456789,
    uploadDate: '2024-03-10',
    knowledgeBaseId: 3,
    status: 'processing'
  }
])

const uploadDialogVisible = ref(false)
const selectedFiles = ref<FileList | null>(null)
const selectedKnowledgeBase = ref<number>(1)

const formatFileSize = (bytes: number): string => {
  if (bytes < 1024) return bytes + ' B'
  else if (bytes < 1048576) return (bytes / 1024).toFixed(1) + ' KB'
  else return (bytes / 1048576).toFixed(1) + ' MB'
}

const handleFileChange = (event: Event) => {
  const target = event.target as HTMLInputElement
  selectedFiles.value = target.files
}

const uploadDocuments = async () => {
  if (!selectedFiles.value || selectedFiles.value.length === 0) {
    ElMessage.warning('请选择要上传的文件')
    return
  }

  if (selectedFiles.value.length > 5) {
    ElMessage.warning('每次最多只能上传5个文件')
    return
  }

  // 模拟上传过程
  for (let i = 0; i < selectedFiles.value.length; i++) {
    const file = selectedFiles.value[i]
    if (file) { // 类型检查
      const newDoc: Document = {
        id: documents.value.length + 1,
        filename: file.name.toLowerCase().replace(/\s+/g, '_'),
        originalName: file.name,
        size: file.size,
        uploadDate: new Date().toISOString().split('T')[0] as string,
        knowledgeBaseId: selectedKnowledgeBase.value,
        status: 'uploading'
      }
      
      documents.value.unshift(newDoc)
      
      // 模拟处理过程
      const tempNewDoc = {...newDoc} // 避免闭包中的类型问题
      setTimeout(() => {
        const docIndex = documents.value.findIndex(doc => doc.id === tempNewDoc.id)
        if (docIndex !== -1) {
          documents.value[docIndex].status = 'processing'
          
          setTimeout(() => {
            if (docIndex < documents.value.length) {
              documents.value[docIndex].status = 'completed'
            }
          }, 2000)
        }
      }, 1000)
    }
  }

  ElMessage.success('文件上传成功')
  uploadDialogVisible.value = false
  selectedFiles.value = null
}

const deleteDocument = async (id: number) => {
  try {
    await ElMessageBox.confirm(
      '确定要删除这个文档吗？此操作不可恢复。',
      '确认删除',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
    )
    
    documents.value = documents.value.filter(doc => doc.id !== id)
    ElMessage.success('文档删除成功')
  } catch {
    // 用户取消删除
  }
}
</script>

<template>
  <div class="documents-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>文档管理</span>
          <el-button type="primary" @click="uploadDialogVisible = true">上传文档</el-button>
        </div>
      </template>
      
      <el-table :data="documents" style="width: 100%" stripe>
        <el-table-column prop="originalName" label="文档名称" min-width="200">
          <template #default="{ row }">
            <div class="file-info">
              <el-icon><Document /></el-icon>
              <span>{{ row.originalName }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="size" label="大小" width="120">
          <template #default="{ row }">
            {{ formatFileSize(row.size) }}
          </template>
        </el-table-column>
        <el-table-column prop="uploadDate" label="上传日期" width="120" />
        <el-table-column prop="knowledgeBaseId" label="所属知识库" width="150">
          <template #default="{ row }">
            <el-tag v-if="row.knowledgeBaseId === 1" type="info">公司政策知识库</el-tag>
            <el-tag v-else-if="row.knowledgeBaseId === 2" type="success">产品文档知识库</el-tag>
            <el-tag v-else-if="row.knowledgeBaseId === 3" type="warning">培训资料知识库</el-tag>
            <el-tag v-else type="info">未知知识库</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="120">
          <template #default="{ row }">
            <el-tag 
              :type="row.status === 'completed' ? 'success' : 
                     row.status === 'processing' ? 'warning' : 
                     row.status === 'uploading' ? 'info' : 'danger'"
            >
              {{ row.status === 'completed' ? '已完成' : 
                 row.status === 'processing' ? '处理中' : 
                 row.status === 'uploading' ? '上传中' : '失败' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button 
              type="danger" 
              size="small" 
              @click="deleteDocument(row.id)"
            >
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 上传文档对话框 -->
    <el-dialog v-model="uploadDialogVisible" title="上传文档" width="500px">
      <el-form label-width="100px">
        <el-form-item label="选择文件">
          <el-upload
            class="upload-demo"
            drag
            multiple
            accept=".pdf,.doc,.docx,.txt,.md"
            :auto-upload="false"
            @change="handleFileChange"
          >
            <el-icon class="el-icon--upload"><upload-filled /></el-icon>
            <div class="el-upload__text">
              将文件拖拽到此处，或<em>点击上传</em>
            </div>
            <template #tip>
              <div class="el-upload__tip">
                支持 PDF, Word, TXT, Markdown 格式的文档，单次最多上传5个文件
              </div>
            </template>
          </el-upload>
        </el-form-item>
        <el-form-item label="知识库">
          <el-select v-model="selectedKnowledgeBase" placeholder="请选择知识库" style="width: 100%">
            <el-option label="公司政策知识库" :value="1" />
            <el-option label="产品文档知识库" :value="2" />
            <el-option label="培训资料知识库" :value="3" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="uploadDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="uploadDocuments">上传</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.documents-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.file-info {
  display: flex;
  align-items: center;
  gap: 8px;
}

.file-info .el-icon {
  color: #409eff;
}
</style>