文创购物商城，采用SpringBoot3 + MybatisPlus + Redis + Vue3 + ElementPlus，前后端分离系统实现支付宝沙盒付款功能，文件上传到阿里巴巴对象存储服务等。自己部署时请更改邮箱服务、阿里云OSS、支付宝支付、百度地图等为自己的。

访问  http://wcgmallcwj.online:88     查看效果，账户自己使用邮箱注册即可

前端代码：https://gitee.com/CWJ991203/vue-wcg-mall-user

后台管理代码：https://gitee.com/CWJ991203/vue-wcg-mall-admin

部分效果如下

![输入图片说明](images/1.jpg)
![输入图片说明](images/2.jpg)
![输入图片说明](images/3.jpg)
![输入图片说明](images/4.jpg)
![输入图片说明](images/5.jpg)

# WCG Mall

一个基于 Java 的电商商城系统，支持商品浏览、下单、购物车管理、用户注册登录等核心功能，适合作为学习项目或基础电商平台开发的起点。

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
📝 TODO
✅ 商品浏览

✅ 用户登录注册

⬜ 支付集成

⬜ 商品评论系统

⬜ 后台管理页面

🤝 贡献
欢迎提交 Issue 或 Pull Request 来改进项目！
