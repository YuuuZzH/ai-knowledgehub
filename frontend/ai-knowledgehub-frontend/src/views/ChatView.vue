<script setup lang="ts">
import { ref } from 'vue'

defineOptions({
  name: 'ChatView'
})

const message = ref('')
const messages = ref([
  { id: 1, content: '欢迎来到AI知识库问答系统！您可以在这里提出问题，我会基于知识库为您提供答案。', role: 'assistant', timestamp: new Date() }
])

const sendMessage = () => {
  if (!message.value.trim()) return
  
  // 添加用户消息
  messages.value.push({
    id: messages.value.length + 1,
    content: message.value,
    role: 'user',
    timestamp: new Date()
  })
  
  // 模拟AI回复
  setTimeout(() => {
    messages.value.push({
      id: messages.value.length + 1,
      content: '这是AI助手的回复，实际实现中会连接后端进行查询。',
      role: 'assistant',
      timestamp: new Date()
    })
  }, 1000)
  
  message.value = ''
}
</script>

<template>
  <div class="chat-container">
    <el-card class="chat-header">
      <template #header>
        <div class="card-header">
          <span>AI知识库问答</span>
        </div>
      </template>
      <div class="chat-messages">
        <div v-for="msg in messages" :key="msg.id" class="message-item" :class="{ 'user-message': msg.role === 'user', 'assistant-message': msg.role === 'assistant' }">
          <div class="message-content">{{ msg.content }}</div>
          <div class="message-timestamp">{{ msg.timestamp.toLocaleTimeString() }}</div>
        </div>
      </div>
    </el-card>
    
    <el-card class="chat-input-area">
      <el-input
        v-model="message"
        :rows="4"
        type="textarea"
        placeholder="请输入您的问题..."
        maxlength="500"
        show-word-limit
        @keyup.enter="sendMessage"
      />
      <div class="input-actions">
        <el-button type="primary" @click="sendMessage">发送</el-button>
      </div>
    </el-card>
  </div>
</template>

<style scoped>
.chat-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
  height: calc(100vh - 40px);
  display: flex;
  flex-direction: column;
}

.chat-header {
  flex: 1;
  margin-bottom: 20px;
  display: flex;
  flex-direction: column;
}

.chat-messages {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
  background-color: #fafafa;
}

.message-item {
  margin-bottom: 15px;
  padding: 10px 15px;
  border-radius: 8px;
  max-width: 80%;
}

.user-message {
  background-color: #dfeeff;
  margin-left: auto;
  text-align: right;
}

.assistant-message {
  background-color: white;
  border: 1px solid #eee;
}

.message-timestamp {
  font-size: 0.7em;
  color: #999;
  margin-top: 5px;
}

.chat-input-area {
  margin-top: auto;
}

.input-actions {
  margin-top: 15px;
  text-align: right;
}
</style>