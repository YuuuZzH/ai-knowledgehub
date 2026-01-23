<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElRow, ElCol, ElCard, ElButton } from 'element-plus'

const router = useRouter()

// 模拟数据
const knowledgeBaseCount = ref(0)
const documentCount = ref(0)
const chatCount = ref(0)
const userCount = ref(0)

onMounted(() => {
  // 加载统计数据
  loadData()
})

const loadData = () => {
  // 模拟加载数据
  knowledgeBaseCount.value = Math.floor(Math.random() * 100)
  documentCount.value = Math.floor(Math.random() * 1000)
  chatCount.value = Math.floor(Math.random() * 10000)
  userCount.value = Math.floor(Math.random() * 50)
}

const goToKnowledgeBase = () => {
  router.push({ name: 'knowledge' })
}

const goToDocuments = () => {
  router.push({ name: 'documents' })
}

const goToChat = () => {
  router.push({ name: 'chat' })
}

const goToProfile = () => {
  router.push({ name: 'profile' })
}
</script>

<template>
  <div class="dashboard-container">
    <el-row :gutter="20" class="dashboard-header">
      <el-col :span="24">
        <h1>AI知识库系统 - 仪表盘</h1>
        <p>欢迎使用企业智能知识问答系统</p>
      </el-col>
    </el-row>
    
    <el-row :gutter="20" class="dashboard-cards">
      <el-col :span="6">
        <el-card class="dashboard-card">
          <template #header>
            <div class="card-title">知识库数量</div>
          </template>
          <div class="card-content">{{ knowledgeBaseCount }}</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="dashboard-card">
          <template #header>
            <div class="card-title">文档总数</div>
          </template>
          <div class="card-content">{{ documentCount }}</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="dashboard-card">
          <template #header>
            <div class="card-title">问答次数</div>
          </template>
          <div class="card-content">{{ chatCount }}</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="dashboard-card">
          <template #header>
            <div class="card-title">活跃用户</div>
          </template>
          <div class="card-content">{{ userCount }}</div>
        </el-card>
      </el-col>
    </el-row>
    
    <el-row :gutter="20" class="quick-links">
      <el-col :span="24">
        <h3>快速导航</h3>
        <div class="link-buttons">
          <el-button type="primary" size="large" @click="goToKnowledgeBase">知识库管理</el-button>
          <el-button type="success" size="large" @click="goToDocuments">文档管理</el-button>
          <el-button type="info" size="large" @click="goToChat">智能问答</el-button>
          <el-button type="warning" size="large" @click="goToProfile">个人中心</el-button>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped>
.dashboard-container {
  padding: 20px;
}

.dashboard-header {
  margin-bottom: 30px;
  text-align: center;
}

.dashboard-header h1 {
  color: #303133;
  margin-bottom: 10px;
}

.dashboard-cards {
  margin-bottom: 30px;
}

.dashboard-card {
  text-align: center;
}

.card-title {
  font-weight: bold;
  font-size: 14px;
}

.card-content {
  font-size: 24px;
  font-weight: bold;
  color: #409EFF;
  margin: 15px 0;
}

.quick-links {
  margin-top: 40px;
}

.quick-links h3 {
  margin-bottom: 20px;
}

.link-buttons {
  display: flex;
  gap: 20px;
  justify-content: center;
}
</style>
