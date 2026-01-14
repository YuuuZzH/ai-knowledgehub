# AI知识库后端系统

基于 Spring Boot 3 + MyBatis-Plus + PostgreSQL + LangChain4j 构建的企业级智能知识问答系统后端。

## 项目结构

```
src/main/java/online/yuuu/aiknowledgehub/
├── common/               # 通用类
│   └── Result.java       # 统一返回结果
├── config/               # 配置类
│   └── MybatisPlusConfig.java # MyBatis-Plus配置
├── controller/           # 控制器层
│   ├── AuthController.java   # 认证控制器
│   ├── UserController.java   # 用户控制器
│   ├── KnowledgeBaseController.java # 知识库控制器
│   ├── DocumentController.java # 文档控制器
│   └── ChatController.java   # 聊天控制器
├── dto/                  # 数据传输对象
│   ├── LoginRequest.java     # 登录请求
│   ├── RegisterRequest.java  # 注册请求
│   ├── UpdateProfileRequest.java # 更新个人信息请求
│   ├── KnowledgeBaseCreateRequest.java # 知识库创建请求
│   ├── KnowledgeBaseUpdateRequest.java # 知识库更新请求
│   ├── ChatRequest.java      # 聊天请求
│   └── ChatResponse.java     # 聊天响应
├── entity/               # 实体类
│   ├── User.java             # 用户实体
│   ├── KnowledgeBase.java    # 知识库实体
│   └── Document.java         # 文档实体
├── mapper/               # 数据访问层
│   ├── UserMapper.java       # 用户数据访问
│   ├── KnowledgeBaseMapper.java # 知识库数据访问
│   └── DocumentMapper.java   # 文档数据访问
├── service/              # 业务逻辑接口
│   ├── IUserService.java     # 用户服务接口
│   ├── IKnowledgeBaseService.java # 知识库服务接口
│   ├── IDocumentService.java # 文档服务接口
│   └── IChatService.java     # 聊天服务接口
├── service/impl/         # 业务逻辑实现
│   ├── UserServiceImpl.java  # 用户服务实现
│   ├── KnowledgeBaseServiceImpl.java # 知识库服务实现
│   ├── DocumentServiceImpl.java # 文档服务实现
│   └── ChatServiceImpl.java  # 聊天服务实现
└── AiKnowledgehubApplication.java # 主应用类
```

## 功能模块

### 1. 用户管理
- 用户注册/登录
- 个人信息管理
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

- **框架**: Spring Boot 3.5.6
- **语言**: Java 21
- **持久层**: MyBatis-Plus
- **数据库**: PostgreSQL
- **向量数据库**: pgvector (PostgreSQL扩展)
- **大模型集成**: LangChain4j
- **文档解析**: Apache Tika
- **加密**: BCrypt

## 环境配置

修改 `src/main/resources/application.properties` 文件配置数据库连接：

```properties
# 数据库配置
spring.datasource.url=jdbc:postgresql://localhost:5432/ai_knowledgehub
spring.datasource.username=your_username
spring.datasource.password=your_password
```

## 启动项目

```bash
# 使用Maven启动
mvn spring-boot:run

# 或打包后运行
mvn clean package
java -jar target/ai-knowledgehub-0.0.1-SNAPSHOT.jar
```

## API 接口

### 认证相关
- POST `/api/auth/login` - 用户登录
- POST `/api/auth/register` - 用户注册

### 用户相关
- GET `/api/users/me?userId={id}` - 获取当前用户信息
- PUT `/api/users/me?userId={id}` - 更新用户信息
- POST `/api/users/change-password?userId={id}&oldPassword={old}&newPassword={new}` - 修改密码

### 知识库相关
- GET `/api/knowledge-bases` - 获取所有知识库
- GET `/api/knowledge-bases/{id}` - 获取单个知识库
- POST `/api/knowledge-bases?userId={id}` - 创建知识库
- PUT `/api/knowledge-bases/{id}?userId={id}` - 更新知识库
- DELETE `/api/knowledge-bases/{id}?userId={id}` - 删除知识库

### 文档相关
- GET `/api/documents?knowledgeBaseId={id}` - 获取文档列表
- GET `/api/documents/{id}` - 获取单个文档
- POST `/api/documents/upload` - 上传文档
- DELETE `/api/documents/{id}` - 删除文档

### 聊天相关
- POST `/api/chat` - 发送消息
- POST `/api/chat/start` - 开始新聊天

## 数据库设计

### 用户表 (users)
- id: 主键
- username: 用户名
- email: 邮箱
- password: 密码(加密)
- role: 角色
- avatar: 头像
- last_login: 最后登录时间
- created_at: 创建时间
- updated_at: 更新时间

### 知识库表 (knowledge_bases)
- id: 主键
- name: 知识库名称
- description: 描述
- user_id: 所属用户
- is_public: 是否公开
- created_at: 创建时间
- updated_at: 更新时间

### 文档表 (documents)
- id: 主键
- filename: 文件名
- original_name: 原始文件名
- size: 文件大小
- knowledge_base_id: 所属知识库
- status: 状态
- file_path: 文件路径
- upload_date: 上传日期
- created_at: 创建时间
- updated_at: 更新时间

## 注意事项

1. 需要在PostgreSQL中安装pgvector扩展以支持向量存储
2. 需要配置Ollama或其他大模型服务
3. 文档处理和向量化功能需要额外实现
4. JWT认证功能需要完善

---

本项目为AI知识库系统的后端部分，与前端项目配合使用。