# vue-wcg-mall-admin

这是一个基于Vue的商城后台管理系统，旨在为开发者提供一个高效、便捷的管理界面。系统集成了商品管理、订单管理、用户管理、博客管理等功能模块，适用于电商后台管理场景。

## 功能特性

- **商品管理**：支持商品信息的增删改查，包括商品详情、库存、价格等信息。
- **订单管理**：提供订单状态的查看与更新功能，支持订单发货等操作。
- **用户管理**：管理用户账户信息，支持用户权限设置。
- **博客管理**：发布和管理博客文章，支持文章的上下架操作。
- **首页轮播图管理**：设置和管理首页展示的轮播图片。

## 技术栈

- Vue 3
- Element Plus
- Pinia（状态管理）
- Vue Router
- Axios（网络请求）

## 安装与运行

1. 克隆仓库：
   ```bash
   git clone https://gitee.com/CWJ991203/vue-wcg-mall-admin.git
   ```

2. 进入项目目录并安装依赖：
   ```bash
   cd vue-wcg-mall-admin
   npm install
   ```

3. 启动开发服务器：
   ```bash
   npm run dev
   ```


## 使用说明

- 登录系统后，可通过左侧导航栏访问各个功能模块。
- 各个管理页面提供了直观的操作按钮，支持数据的编辑、删除、详情查看等操作。
- 所有数据操作均通过API与后端服务交互，确保数据的一致性和安全性。

## 贡献指南

欢迎贡献代码和提出建议！请遵循以下步骤：

1. Fork 项目仓库。
2. 创建新分支 (`git checkout -b feature/new-feature`)。
3. 提交更改 (`git commit -am 'Add new feature'`)。
4. 推送分支 (`git push origin feature/new-feature`)。
5. 提交 Pull Request。
