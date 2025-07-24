
# WCG Mall

一个基于 Java 的商城系统，支持商品浏览、下单、购物车管理、用户注册登录、博客等核心功能，适合作为学习项目或基础电商平台开发的起点。

访问  http://wcgmallcwj.online:88 查看效果

账户：1774532899@qq.com   123456

用户端代码：https://gitee.com/CWJ991203/vue-wcg-mall-user

后台管理代码：https://gitee.com/CWJ991203/vue-wcg-mall-admin

## 🧰 技术栈

- **后端：** Spring Boot, MyBatis
- **前端：** Vue3, ElementPlus
- **数据库：** MySQL
- **其他：** Maven, Lombok, Redis

## 📦 功能模块

- 用户注册与登录
- 商品分类浏览与搜索
- 购物车管理
- 订单提交与支付（支付宝沙盒）
- 后台商品管理与用户管理

## 🚀 快速开始

### 环境准备

- JDK 17
- MySQL 5.7+
- Maven 3.6+
- IDEA 或 Eclipse（推荐 IntelliJ IDEA）

### 克隆项目

```bash
git clone https://gitee.com/CWJ991203/wcg_mall.git
cd wcg_mall

配置数据库
创建数据库：wcg_mall

执行建表语句

修改 application.yml 或 application.properties 中的数据库连接信息

启动项目

📁 项目结构
wcg_mall/
├── src/
│   ├── main/
│   │   ├── java/          // Java 后端代码
│   │   ├── resources/     // 配置文件和模板资源
│   │   └── webapp/        // 前端页面（如果使用 JSP/HTML）
├── pom.xml                // Maven 项目配置
└── README.md

部分效果如下

![输入图片说明](images/1.jpg)
![输入图片说明](images/2.jpg)
![输入图片说明](images/3.jpg)
![输入图片说明](images/4.jpg)
![输入图片说明](images/5.jpg)
🤝 贡献
欢迎提交 Issue 或 Pull Request 来改进项目！
