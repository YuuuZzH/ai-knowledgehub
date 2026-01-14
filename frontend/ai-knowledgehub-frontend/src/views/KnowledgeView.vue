<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'

defineOptions({
  name: 'KnowledgeView'
})

interface KnowledgeBase {
  id: number
  name: string
  description: string
  documentCount: number
  createdAt: string
  isPublic: boolean
}

const knowledgeBases = ref<KnowledgeBase[]>([
  {
    id: 1,
    name: '公司政策知识库',
    description: '包含公司各项政策和规章制度',
    documentCount: 15,
    createdAt: '2024-01-15',
    isPublic: true
  },
  {
    id: 2,
    name: '产品文档知识库',
    description: '产品使用说明和技术文档',
    documentCount: 23,
    createdAt: '2024-02-20',
    isPublic: false
  },
  {
    id: 3,
    name: '培训资料知识库',
    description: '员工培训相关资料和课程',
    documentCount: 8,
    createdAt: '2024-03-10',
    isPublic: false
  }
])

const dialogVisible = ref(false)
const currentKnowledgeBase = ref<KnowledgeBase | null>(null)
interface KnowledgeBaseForm {
  name: string
  description: string
  isPublic: boolean
}

const form = ref<KnowledgeBaseForm>({
  name: '',
  description: '',
  isPublic: false
} as KnowledgeBaseForm)

const createKnowledgeBase = () => {
  currentKnowledgeBase.value = null
  form.value = {
    name: '',
    description: '',
    isPublic: false
  }
  dialogVisible.value = true
}

const editKnowledgeBase = (kb: KnowledgeBase) => {
  currentKnowledgeBase.value = kb
  form.value = {
    name: kb.name,
    description: kb.description,
    isPublic: kb.isPublic
  }
  dialogVisible.value = true
}

const saveKnowledgeBase = () => {
  if (!form.value.name.trim()) {
    ElMessage.error('请输入知识库名称')
    return
  }

  if (currentKnowledgeBase.value) {
    // 编辑现有知识库
    const index = knowledgeBases.value.findIndex(kb => kb.id === currentKnowledgeBase.value?.id)
    if (index !== -1) {
      const updatedKb = {...knowledgeBases.value[index]}
      updatedKb.name = form.value.name
      updatedKb.description = form.value.description
      updatedKb.isPublic = form.value.isPublic
      knowledgeBases.value[index] = updatedKb
    }
  } else {
    // 创建新知识库
    const newKb: KnowledgeBase = {
      id: knowledgeBases.value.length + 1,
      name: form.value.name || '',
      description: form.value.description || '',
      documentCount: 0,
      createdAt: new Date().toISOString().split('T')[0],
      isPublic: form.value.isPublic
    }
    knowledgeBases.value.push(newKb)
  }

  ElMessage.success(currentKnowledgeBase.value ? '知识库更新成功' : '知识库创建成功')
  dialogVisible.value = false
}

const deleteKnowledgeBase = (id: number) => {
  ElMessage.success('知识库删除成功') // 实际项目中应调用API
  knowledgeBases.value = knowledgeBases.value.filter(kb => kb.id !== id)
}
</script>

<template>
  <div class="knowledge-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>知识库管理</span>
          <el-button type="primary" @click="createKnowledgeBase">新建知识库</el-button>
        </div>
      </template>
      
      <el-row :gutter="20">
        <el-col 
          v-for="kb in knowledgeBases" 
          :key="kb.id" 
          :span="8"
        >
          <el-card class="kb-card">
            <template #header>
              <div class="kb-header">
                <span class="kb-name">{{ kb.name }}</span>
                <div>
                  <el-button 
                    type="primary" 
                    size="small" 
                    text 
                    @click="editKnowledgeBase(kb)"
                  >
                    编辑
                  </el-button>
                  <el-button 
                    type="danger" 
                    size="small" 
                    text 
                    @click="deleteKnowledgeBase(kb.id)"
                  >
                    删除
                  </el-button>
                </div>
              </div>
            </template>
            
            <div class="kb-content">
              <p class="kb-description">{{ kb.description }}</p>
              <div class="kb-stats">
                <el-tag type="info" size="small">文档数: {{ kb.documentCount }}</el-tag>
                <el-tag :type="kb.isPublic ? 'success' : 'warning'" size="small">
                  {{ kb.isPublic ? '公开' : '私有' }}
                </el-tag>
              </div>
              <div class="kb-date">创建时间: {{ kb.createdAt }}</div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </el-card>

    <!-- 知识库编辑对话框 -->
    <el-dialog 
      v-model="dialogVisible" 
      :title="currentKnowledgeBase ? '编辑知识库' : '新建知识库'"
      width="500px"
    >
      <el-form :model="form" label-width="100px">
        <el-form-item label="知识库名称">
          <el-input v-model="form.name" placeholder="请输入知识库名称" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input 
            v-model="form.description" 
            type="textarea" 
            :rows="3"
            placeholder="请输入知识库描述"
          />
        </el-form-item>
        <el-form-item label="访问权限">
          <el-switch
            v-model="form.isPublic"
            active-text="公开"
            inactive-text="私有"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="saveKnowledgeBase">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.knowledge-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.kb-card {
  margin-bottom: 20px;
  height: 200px;
  display: flex;
  flex-direction: column;
}

.kb-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.kb-name {
  font-weight: bold;
  font-size: 16px;
}

.kb-content {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.kb-description {
  color: #666;
  margin-bottom: 10px;
  flex: 1;
}

.kb-stats {
  display: flex;
  gap: 8px;
  margin-bottom: 10px;
}

.kb-date {
  font-size: 12px;
  color: #999;
}
</style>