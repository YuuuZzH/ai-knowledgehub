<script setup lang="ts">
import { ref } from 'vue'
import { ElMessage } from 'element-plus'

defineOptions({
  name: 'ProfileView'
})

interface UserProfile {
  id: number
  username: string
  email: string
  role: string
  avatar: string
  lastLogin: string
  joinDate: string
}

const profile = ref<UserProfile>({
  id: 1,
  username: 'admin',
  email: 'admin@example.com',
  role: '管理员',
  avatar: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
  lastLogin: '2024-05-15 14:30:25',
  joinDate: '2023-01-10'
})

const form = ref({
  username: profile.value.username,
  email: profile.value.email,
  newPassword: '',
  confirmPassword: ''
})

const isEditing = ref(false)

const toggleEdit = () => {
  isEditing.value = !isEditing.value
  if (!isEditing.value) {
    // 取消编辑，恢复原始值
    form.value = {
      username: profile.value.username,
      email: profile.value.email,
      newPassword: '',
      confirmPassword: ''
    }
  }
}

const saveProfile = () => {
  if (!form.value.username.trim()) {
    ElMessage.error('请输入用户名')
    return
  }
  
  if (!form.value.email.trim()) {
    ElMessage.error('请输入邮箱地址')
    return
  }
  
  if (form.value.newPassword && form.value.newPassword !== form.value.confirmPassword) {
    ElMessage.error('两次输入的密码不一致')
    return
  }
  
  // 更新用户信息
  profile.value.username = form.value.username
  profile.value.email = form.value.email
  
  ElMessage.success('个人信息更新成功')
  isEditing.value = false
}
</script>

<template>
  <div class="profile-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>个人中心</span>
        </div>
      </template>
      
      <div class="profile-content">
        <div class="profile-avatar">
          <el-avatar :size="120" :src="profile.avatar" />
          <div class="avatar-actions">
            <el-upload
              class="avatar-uploader"
              action="/api/upload"
              :show-file-list="false"
              :on-success="() => {}"
              :before-upload="() => true"
            >
              <el-button type="primary" size="small">更换头像</el-button>
            </el-upload>
          </div>
        </div>
        
        <div class="profile-details">
          <el-descriptions :column="1" border>
            <el-descriptions-item label="用户ID">
              {{ profile.id }}
            </el-descriptions-item>
            <el-descriptions-item label="用户名">
              <template v-if="isEditing">
                <el-input v-model="form.username" />
              </template>
              <template v-else>
                {{ profile.username }}
              </template>
            </el-descriptions-item>
            <el-descriptions-item label="邮箱">
              <template v-if="isEditing">
                <el-input v-model="form.email" />
              </template>
              <template v-else>
                {{ profile.email }}
              </template>
            </el-descriptions-item>
            <el-descriptions-item label="角色">
              {{ profile.role }}
            </el-descriptions-item>
            <el-descriptions-item label="加入日期">
              {{ profile.joinDate }}
            </el-descriptions-item>
            <el-descriptions-item label="最后登录">
              {{ profile.lastLogin }}
            </el-descriptions-item>
          </el-descriptions>
          
          <div v-if="isEditing" class="password-section">
            <h3>更改密码</h3>
            <el-form :model="form" label-width="100px" style="max-width: 400px;">
              <el-form-item label="新密码">
                <el-input 
                  v-model="form.newPassword" 
                  type="password" 
                  placeholder="留空则不修改密码"
                />
              </el-form-item>
              <el-form-item label="确认密码">
                <el-input 
                  v-model="form.confirmPassword" 
                  type="password" 
                  placeholder="请再次输入新密码"
                />
              </el-form-item>
            </el-form>
          </div>
          
          <div class="actions">
            <el-button v-if="!isEditing" type="primary" @click="toggleEdit">编辑信息</el-button>
            <template v-else>
              <el-button @click="toggleEdit">取消</el-button>
              <el-button type="primary" @click="saveProfile">保存</el-button>
            </template>
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<style scoped>
.profile-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.profile-content {
  display: flex;
  gap: 40px;
}

.profile-avatar {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.avatar-actions {
  margin-top: 15px;
}

.profile-details {
  flex: 1;
}

.password-section {
  margin-top: 20px;
  padding-top: 20px;
  border-top: 1px solid #eee;
}

.actions {
  margin-top: 20px;
  text-align: right;
}
</style>