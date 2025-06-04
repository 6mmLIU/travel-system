# 旅游网站系统

## 项目简介
旅游网站系统旨在为用户和商家提供一个便捷的旅游管理平台。用户可以浏览、查询和收藏旅游线路，而商家可以发布、上下架旅游线路，并进行管理。该系统采用 **Spring Boot** 进行后端开发，前端使用 **Thymeleaf** 进行页面渲染，并结合 **MySQL** 进行数据存储。

## 技术栈
- **后端**：Spring Boot、Spring Security、MyBatis、Redis
- **前端**：Thymeleaf、HTML、CSS、JavaScript
- **数据库**：MySQL（端口：`3307`）
- **工具**：Maven、Lombok

## 功能列表
### 用户端
✅ 用户注册与登录（Spring Security 认证）  
✅ 旅游线路查询  
✅ 旅游线路收藏功能  
✅ 用户信息管理  
✅ 订单管理  

### 商家端
✅ 旅游信息发布  
✅ 旅游线路上下架  
✅ 已发布线路管理  
✅ 订单处理  

### 管理员端
✅ 后台管理功能  
✅ 线路审核与管理  
✅ 用户管理  
✅ 订单管理  

## 项目结构
```
TravelSystem/
│── src/
│   ├── main/
│   │   ├── java/com/example/travelsystem/
│   │   │   ├── config/         # 配置类（Spring Security、Redis等）
│   │   │   ├── controller/     # 控制器
│   │   │   ├── mapper/         # 数据访问层
│   │   │   ├── model/          # 实体类
│   │   │   ├── service/        # 业务逻辑
│   │   │   ├── utils/          # 工具类
│   │   │   ├── TravelSystemApplication.java  # 启动类
│   │   ├── resources/
│   │   │   ├── mapper/         # MyBatis XML 映射文件
│   │   │   ├── static/         # 静态资源（CSS/Images）
│   │   │   ├── templates.auth/ # Thymeleaf前端页面
│   │   │   ├── application.yml # 配置文件
│── pom.xml
│── README.md
```

## 环境要求
- JDK 17+
- MySQL 8+（端口 `3307`）
- Maven 3.8+
- Redis（可选，用于缓存）

## 部署步骤
### 1. 数据库初始化
```sql
CREATE DATABASE travelsystem;
```
接着在数据库中执行根目录下的 `travelsystem.sql`，创建所需的表：

```sh
mysql -u root -p travelsystem < travelsystem.sql
```
修改 `application.yml` 中的数据库配置，确保 `spring.datasource.url` 指向 `jdbc:mysql://localhost:3307/travelsystem`。

### 2. 运行项目
```sh
mvn spring-boot:run
```

### 3. 访问系统
- 用户前台：`http://localhost:8080/`
- 后台管理：`http://localhost:8080/admin`

## 未来计划
- ✅ 增加旅游路线评论功能
- ✅ 使用 WebSocket 实现商家与用户的实时沟通
- ✅ 支持多语言切换

## 贡献指南
1. Fork 本项目
2. 创建新分支 (`git checkout -b feature-new`)
3. 提交更改 (`git commit -m '新增功能'`)
4. Push 分支 (`git push origin feature-new`)
5. 提交 Pull Request

---
💡 **如果你对该项目有任何问题或建议，欢迎提交 Issue 或联系开发者！**
