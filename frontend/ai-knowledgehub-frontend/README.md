# AI知识库前端系统

基于 Vue3 + Vite + TypeScript + Element Plus 构建的企业级智能知识问答系统前端。

## 项目结构

```
src/
├── api/                    # API 接口定义
│   ├── index.ts           # API 客户端配置
│   ├── api.ts             # API 统一入口
│   ├── chatApi.ts         # 问答相关API
│   ├── documentApi.ts     # 文档相关API
│   ├── knowledgeBaseApi.ts # 知识库相关API
│   └── userApi.ts         # 用户相关API
├── assets/                # 静态资源
├── components/            # 公共组件
├── router/                # 路由配置
├── stores/                # Pinia 状态管理
│   ├── index.ts           # Store 统一入口
│   ├── counter.ts         # 计数器 Store（示例）
│   ├── userStore.ts       # 用户状态管理
│   ├── knowledgeBaseStore.ts # 知识库状态管理
│   ├── documentStore.ts   # 文档状态管理
│   └── chatStore.ts       # 聊天状态管理
├── views/                 # 页面视图
│   ├── AboutView.vue      # 关于页面
│   ├── ChatView.vue       # 问答页面
│   ├── DocumentsView.vue  # 文档管理页面
│   ├── HomeView.vue       # 首页
│   ├── KnowledgeView.vue  # 知识库管理页面
│   └── ProfileView.vue    # 个人中心页面
└── main.ts                # 主入口文件
```

## 功能模块

### 1. 用户管理
- 用户登录/注册
- 个人资料管理
- 密码修改

### 2. 知识库管理
- 创建/编辑/删除知识库
- 知识库权限设置（公开/私有）
- 知识库统计信息

### 3. 文档管理
- 支持多种格式文档上传（PDF, Word, TXT, Markdown）
- 文档状态跟踪（上传中/处理中/已完成）
- 文档删除功能

### 4. 智能问答
- 基于知识库的智能问答
- 聊天记录管理
- 会话管理

## 技术栈

- **框架**: Vue 3 (Composition API)
- **构建工具**: Vite
- **语言**: TypeScript
- **UI组件库**: Element Plus
- **状态管理**: Pinia
- **HTTP客户端**: Axios
- **路由**: Vue Router

## 环境配置

复制 `.env.example` 文件为 `.env` 并根据实际情况修改配置：

```bash
VITE_API_BASE_URL=http://localhost:8080/api  # 后端API地址
```

## 安装与启动

```bash
# 安装依赖
npm install

# 启动开发服务器
npm run dev

# 构建生产版本
npm run build

# 预览生产构建
npm run preview

# 代码格式化
npm run format

# 代码检查
npm run lint
```

## API 接口约定

所有API请求都需要携带认证token（通过请求头 Authorization: Bearer {token}）

### 返回数据格式
```typescript
{
  "code": 200,      // 状态码
  "message": "",    // 消息
  "data": {}        // 数据
}
```

## 状态管理

使用 Pinia 进行全局状态管理，主要包含：

- `userStore`: 用户信息和认证状态
- `knowledgeBaseStore`: 知识库信息
- `documentStore`: 文档管理
- `chatStore`: 聊天会话和消息

## 开发规范

- 使用 TypeScript 进行类型约束
- 组件命名采用 PascalCase
- 文件扩展名使用 `.vue`、`.ts`、`.tsx`
- 提交信息遵循 conventional commits 规范

## 部署

生成静态文件到 `dist/` 目录，可通过任何静态文件服务器提供服务。

---

本项目为AI知识库系统的前端部分，需配合后端服务使用。