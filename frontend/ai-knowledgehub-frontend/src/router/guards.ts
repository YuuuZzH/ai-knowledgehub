import { useUserStore } from '@/stores'
import { ElMessage } from 'element-plus'
import { type RouteLocationNormalized, type NavigationGuardNext } from 'vue-router'

export const authGuard = async (to: RouteLocationNormalized, from: RouteLocationNormalized, next: NavigationGuardNext) => {
  const userStore = useUserStore()
  
  // 定义需要认证的路由
  const requiresAuth = [
    '/dashboard',
    '/chat',
    '/knowledge',
    '/documents',
    '/profile'
  ]
  
  // 检查目标路由是否需要认证
  const needsAuth = requiresAuth.some(path => 
    to.path.startsWith(path)
  )
  
  if (needsAuth) {
    // 如果需要认证但用户未登录
    if (!userStore.isAuthenticated) {
      ElMessage.error('请先登录')
      next({ name: 'login' })
      return
    }
  }
  
  // 如果用户已登录但访问登录/注册页面，重定向到仪表盘
  if (userStore.isAuthenticated && ['/login', '/register'].includes(to.path)) {
    next({ name: 'dashboard' })
    return
  }
  
  next()
}