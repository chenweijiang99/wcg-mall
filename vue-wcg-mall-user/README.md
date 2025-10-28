# vue-wcg-mall-user

一个基于 Vue 的电商用户端商城系统，适用于 PC 端访问，具备完整的商城功能模块。

链接  http://117.72.179.87:83/login

账户：1774532899@qq.com   123456

## 项目安装

确保已安装 Node.js 和 npm。

```bash
# 安装依赖
npm install
```

## 运行项目

```bash
# 启动开发服务器
npm run dev
```

## 构建项目

```bash
# 构建生产环境包
npm run build
```

## 主要功能模块

- **商城功能**：商品浏览、购物车管理、下单支付、订单追踪
- **用户系统**：登录注册、个人信息管理、地址管理、收藏夹
- **内容展示**：博客文章、商品详情、关于我们、联系我们
- **交互功能**：评论系统、商品搜索、筛选与排序

## 技术栈

- Vue 3
- Pinia（状态管理）
- Vue Router
- Vite（构建工具）
- Axios（网络请求）
- SCSS/CSS（样式管理）

## 项目结构

```
src/
├── api/                # 接口请求模块
├── assets/             # 静态资源（图片、字体、样式）
├── components/         # 公共组件（评论、头部、底部等）
├── stores/             # Pinia 状态管理模块
├── utils/              # 工具类（如 request.js）
├── views/              # 页面视图（首页、商品详情、购物车等）
├── router/             # 路由配置
├── event/              # 事件总线
└── main.js             # 入口文件
```

## 依赖管理

项目依赖管理通过 `package.json` 完成，主要依赖包括：

- Vue 核心框架
- Pinia 状态管理
- Vue Router 路由
- Axios 网络请求
- Sass 样式预处理器

## 样式与资源

- 所有 CSS 样式定义在 `src/assets/main.css`
- 图片资源存放在 `src/assets/images/`
- 字体图标使用 Font Awesome

## 状态管理

使用 Pinia 进行状态管理，主要 store 包括：

- `token.js`：管理用户登录 token
- `userInfo.js`：存储用户基本信息
- `wish.js`：管理收藏夹

## 请求封装

网络请求通过 `src/utils/request.js` 封装，统一处理请求拦截、响应拦截和错误处理。

## 贡献指南

欢迎贡献代码和改进项目。请遵循以下步骤：

1. Fork 项目
2. 创建新分支 (`git checkout -b feature/new-feature`)
3. 提交更改 (`git commit -m 'Add new feature'`)
4. 推送分支 (`git push origin feature/new-feature`)
5. 提交 Pull Request
