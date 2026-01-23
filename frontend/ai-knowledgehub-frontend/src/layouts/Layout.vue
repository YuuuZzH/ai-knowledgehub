<template>
  <el-container class="layout-container">
    <!-- 侧边栏 -->
    <el-aside width="200px" class="side-bar">
      <div class="logo">
        <h3>AI知识库</h3>
      </div>
      <el-menu
        :default-active="$route.path"
        class="side-menu"
        :router="true"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b"
      >
        <el-menu-item index="/dashboard">
          <el-icon><House /></el-icon>
          <span>仪表盘</span>
        </el-menu-item>
        <el-menu-item index="/chat">
          <el-icon><ChatLineRound /></el-icon>
          <span>智能问答</span>
        </el-menu-item>
        <el-menu-item index="/knowledge">
          <el-icon><Collection /></el-icon>
          <span>知识库管理</span>
        </el-menu-item>
        <el-menu-item index="/documents">
          <el-icon><Document /></el-icon>
          <span>文档管理</span>
        </el-menu-item>
        <el-menu-item index="/profile">
          <el-icon><User /></el-icon>
          <span>个人中心</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <!-- 主区域 -->
    <el-container>
      <!-- 顶部导航栏 -->
      <el-header class="nav-header">
        <div class="header-left">
          <h2>{{ pageTitle }}</h2>
        </div>
        <div class="header-right">
          <el-dropdown>
            <span class="el-dropdown-link">
              {{ userInfo.username }} <el-icon><ArrowDown /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="goToProfile">个人中心</el-dropdown-item>
                <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>

      <!-- 内容区域 -->
      <el-main class="main-content">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from '@/stores'
import { 
  House, 
  ChatLineRound, 
  Collection, 
  Document, 
  User, 
  ArrowDown 
} from '@element-plus/icons-vue'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

// 当前用户信息
const userInfo = computed(() => userStore.user || { username: '游客' })

// 根据路由动态设置页面标题
const pageTitle = computed(() => {
  const routeMap: Record<string, string> = {
    '/dashboard': '仪表盘',
    '/chat': '智能问答',
    '/knowledge': '知识库管理',
    '/documents': '文档管理',
    '/profile': '个人中心'
  }
  return routeMap[route.path] || 'AI知识库系统'
})

// 退出登录
const logout = () => {
  userStore.logout()
  router.push({ name: 'login' })
}

// 跳转到个人中心
const goToProfile = () => {
  router.push({ name: 'profile' })
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

.side-bar {
  background-color: #545c64;
  color: #fff;
  height: 100vh;
  position: fixed;
  top: 0;
  left: 0;
  bottom: 0;
  z-index: 100;
}

.logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-bottom: 1px solid #444;
}

.logo h3 {
  margin: 0;
  color: #fff;
}

.side-menu {
  border: none;
  height: calc(100% - 60px);
}

.nav-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid #eee;
  padding: 0 20px;
  background-color: #fff;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);
}

.header-left h2 {
  margin: 0;
  font-size: 18px;
  color: #333;
}

.el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
  display: flex;
  align-items: center;
  gap: 5px;
}

.main-content {
  background-color: #f5f7fa;
  padding: 20px;
}
</style>