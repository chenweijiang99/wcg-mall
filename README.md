

# WCG Mall

一个基于Spring Boot的电商系统，包含用户管理、商品管理、订单处理、购物车、心愿单、博客、评论、支付及WebSocket实时通信等模块。

项目链接  http://117.72.179.87:83/login

账户：1774532899@qq.com  123456  

## 🧰 技术栈

- **后端**: Spring Boot, MyBatis Plus, Redis, MySQL, Swagger UI
- **安全**: JWT鉴权、RSA加密、支付宝支付
- **其他**: OSS文件上传、邮件激活与重置、定时任务、缓存（Redis）

## 📦 功能模块

### 用户管理
- 登录 / 注册 / 登出
- 邮件激活
- 密码重置
- 用户信息更新
- 文件上传

### 商品管理
- 商品列表 / 搜索 / 筛选
- 商品详情
- 添加 / 修改 / 删除商品
- 商品上下架
- 商品库存管理

### 订单管理
- 订单创建
- 支付（集成支付宝）
- 退款
- 订单发货
- 订单详情查看
- 用户确认收货 / 取消订单

### 购物车与心愿单
- 添加 / 删除 / 修改购物车商品
- 购物车商品转心愿单
- 心愿单商品转购物车
- 心愿单管理

### 博客与评论
- 博客发布 / 编辑 / 删除
- 获取博客列表 / 热门博客 / 最新博客
- 评论发表 / 删除 / 查询
- 支持评论嵌套结构

### Config配置
- 支付宝配置
- OSS配置
- JWT配置
- Redis缓存
- WebSocket实时通信

### 后台管理
- 商品管理
- 订单管理
- 用户管理
- 品牌管理
- 分类管理
- 博客管理
- 首页轮播图管理
- 官方收藏设置

## 🚀 快速开始

### 0. 环境准备

- Java 8+
- Maven 3.x
- MySQL 5.7+
- Redis 6.x
- 支付宝沙箱账户（用于测试）
- 阿里云OSS账号
- 邮箱服务配置（如QQ邮箱、163邮箱或任意SMTP服务）

### 1. 克隆项目

```bash
git clone https://gitee.com/CWJ991203/wcg_mall
cd wcg_mall
```

### 2. 数据库配置

导入数据库文件 `db.sql` 到你的MySQL数据库

配置数据库连接信息在 `application.yml` 或 `.env` 文件中：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/wcg_mall?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
    username: root
    password: your_password
```

### 3. Redis配置

在 `application.yml` 或 `.env` 中配置Redis连接信息：

```yaml
spring:
  redis:
    host: 127.0.0.1
    port: 6379
```

### 4. 支付宝配置

在 `application.yml` 中配置：

```yaml
mall:
  alipay:
    app-id: your_app_id
    private-key: your_private_key
    alipay-public-key: alipay_public_key
    notify-url: http://yourdomain.com/alipay/notify
    return-url: http://yourdomain.com/alipay/return
    charset: UTF-8
    sign-type: RSA2
    gateway-url: https://openapi.alipaydev.com/gateway.do
```

### 5. 阿里云OSS配置

```yaml
mall:
  alioss:
    endpoint: oss-cn-beijing.aliyuncs.com
    accessKeyId: your_access_key
    accessKeySecret: your_secret_key
    bucketName: your_bucket
```

### 6. JWT配置

```yaml
mall:
  jwt:
    user-secret-key: user_jwt_secret_key
    admin-secret-key: admin_jwt_secret_key
    user-ttl: 86400
    admin-ttl: 86400
```

### 7. 构建并运行

```bash
mvn clean package
java -jar target/wcg_mall.jar
```

或使用开发工具直接运行 `WcgMallApplication.java`

## 📦 API接口

接口文档可通过Swagger UI访问：

```
http://localhost:1203/swagger-ui.html
```
