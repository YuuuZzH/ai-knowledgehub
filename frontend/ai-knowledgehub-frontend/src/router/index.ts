import { createRouter, createWebHistory } from 'vue-router'
import { authGuard } from './guards'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/login', // 默认重定向到登录页
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/LoginView.vue'),
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('../views/RegisterView.vue'),
    },
    // 需要登录保护的路由
    {
      path: '/dashboard',
      name: 'dashboard',
      component: () => import('../layouts/Layout.vue'),
      children: [
        {
          path: '',
          name: 'home',
          component: () => import('../views/HomeView.vue'),
        },
      ],
    },
    {
      path: '/about',
      name: 'about',
      component: () => import('../views/AboutView.vue'),
    },
    // AI Knowledge Hub routes
    {
      path: '/chat',
      name: 'chat',
      component: () => import('../layouts/Layout.vue'),
      children: [
        {
          path: '',
          name: 'chat-inner',
          component: () => import('../views/ChatView.vue'),
        },
      ],
    },
    {
      path: '/knowledge',
      name: 'knowledge',
      component: () => import('../layouts/Layout.vue'),
      children: [
        {
          path: '',
          name: 'knowledge-inner',
          component: () => import('../views/KnowledgeView.vue'),
        },
      ],
    },
    {
      path: '/documents',
      name: 'documents',
      component: () => import('../layouts/Layout.vue'),
      children: [
        {
          path: '',
          name: 'documents-inner',
          component: () => import('../views/DocumentsView.vue'),
        },
      ],
    },
    {
      path: '/profile',
      name: 'profile',
      component: () => import('../layouts/Layout.vue'),
      children: [
        {
          path: '',
          name: 'profile-inner',
          component: () => import('../views/ProfileView.vue'),
        },
      ],
    },
  ],
})

router.beforeEach(authGuard)

export default router
