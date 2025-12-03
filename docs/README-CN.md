# Chat-Zen-Spring

[![License](https://img.shields.io/github/license/Chat-Zen/chat-zen-spring)](LICENSE)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.3-green)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-JDK%2021-red)](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)](https://www.mysql.com/)

## 🌐 其他语言

- [English](../README.md)
- [한국어](README-KR.md)
- [Русский](README-RU.md)

一款基于 Spring Boot 3.3 和 JDK 21 构建的现代化、高性能聊天应用后端。致力于提供实时通信，具备强大的安全机制、可扩展性和性能优化。

## 🚀 主要特性

- **实时通信**: 基于 WebSocket 的即时消息传递
- **用户认证与授权**: Spring Security 配合 JWT 令牌
- **数据持久化**: MySQL 8.0 确保数据可靠存储
- **高性能**: Redis 7.0 缓存优化响应时间
- **异步处理**: RabbitMQ 处理后台任务
- **RESTful API**: 标准 REST 接口
- **可扩展架构**: 支持水平扩展

## 🛠️ 技术栈

### 后端框架
- **[Spring Boot 3.3.3](https://spring.io/projects/spring-boot)**: 应用框架
- **[Java JDK 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)**: 运行环境
- **[Spring Security](https://spring.io/projects/spring-security)**: 认证和授权
- **[Spring Data JPA](https://spring.io/projects/spring-data-jpa)**: 数据访问层
- **[MyBatis-Plus](https://baomidou.com/)**: 增强型 MyBatis 框架
- **[WebSocket](https://docs.spring.io/spring-framework/reference/web/websocket.html)**: 实时通信
- **[Lombok](https://projectlombok.org/)**: 代码生成

### 数据库
- **[MySQL 8.0](https://www.mysql.com/)**: 用户数据和聊天记录主数据库
- **[Redis 7.0](https://redis.io/)**: 高性能缓存和会话管理

### 中间件
- **[RabbitMQ 3](https://www.rabbitmq.com/)**: 消息队列系统
- **[Logback](https://logback.qos.ch/)**: 日志框架

### 其他依赖
- **[JWT](https://jwt.io/)**: 基于令牌的认证
- **[Fastjson2](https://github.com/alibaba/fastjson2)**: JSON 处理
- **[Flyway](https://flywaydb.org/)**: 数据库迁移
- **[Hutool](https://hutool.cn/)**: Java 工具集

## 📋 环境要求

开始之前，请确保已安装以下软件：

- **[Java JDK 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)** 或更高版本
- **[Maven 3.6](https://maven.apache.org/)** 或更高版本
- **[MySQL 8.0](https://www.mysql.com/)** 数据库服务器
- **[Redis 7.0](https://redis.io/)** 服务器
- **[RabbitMQ 3](https://www.rabbitmq.com/)** 消息代理

## 🚀 快速开始

### 1. 克隆仓库

```bash
git clone https://github.com/your-username/chat-zen-spring.git
cd chat-zen-spring
```

### 2. 数据库设置

1. 创建 MySQL 数据库:
```sql
CREATE DATABASE chat_zen_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. 创建数据库用户并授权:
```sql
CREATE USER 'chat_zen_user'@'localhost' IDENTIFIED BY 'strong_password';
GRANT ALL PRIVILEGES ON chat_zen_db.* TO 'chat_zen_user'@'localhost';
FLUSH PRIVILEGES;
```

### 3. 配置环境

1. 在 `src/main/resources/application-dev.yml` 中更新数据库连接信息:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/chat_zen_db?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8
    username: chat_zen_user
    password: strong_password
    driver-class-name: com.mysql.cj.jdbc.Driver

  redis:
    host: localhost
    port: 6379
    # password: your_redis_password # 如需要

  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
```

### 4. 构建项目

```bash
mvn clean compile
```

### 5. 运行应用

```bash
# 使用 Maven
mvn spring-boot:run

# 或构建并运行 JAR 包
mvn clean package
java -jar target/chat-zen-spring-0.0.1-SNAPSHOT.jar
```

## 🔧 配置

应用支持多环境配置:

- **开发环境**: `application-dev.yml`
- **生产环境**: `application-prod.yml`
- **测试环境**: `application-test.yml`

运行特定配置文件:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

## 📡 API 接口

应用提供以下 API 接口:

- `/auth/**` - 认证服务
- `/user/**` - 用户管理服务
- `/ws/**` - WebSocket 实时通信接口
- `/http-test/**` - 测试接口

除登录、注册和健康检查接口外，大部分接口需要认证。

## 🧪 测试

运行测试:

```bash
# 运行所有测试
mvn test

# 运行特定测试类
mvn -Dtest=YourTestClass test
```

## 🔒 安全

应用实现的安全措施包括:

- JWT 基础认证
- Spring Security 配置
- 白名单接口公开访问
- 加密密码存储
- 限流保护

## 🚀 部署

生产环境部署:

1. 构建应用:
   ```bash
   mvn clean package -Pprod
   ```

2. 部署 JAR 文件到服务器:
   ```bash
   java -jar target/chat-zen-spring-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
   ```

## 🤝 贡献

欢迎贡献！参与方式：

1. Fork 仓库
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

### 开发规范

- 遵循现有代码风格
- 为新功能编写测试
- 记录行为变更
- 提交前确保所有测试通过

## 📄 许可证

本项目根据 [LICENSE](LICENSE) 文件中的条款获得许可。

## 📞 联系

- **维护者**: 羡林i
- **邮箱**: 2683971783@qq.com
- **CSDN**: [羡林i-CSDN博客](https://blog.csdn.net/Y_xianlin)
- **Bilibili**: [羡林i的个人空间](https://space.bilibili.com/521082261)

## 🙏 致谢

- 感谢 Spring Boot 团队提供的优秀框架
- 特别感谢所有使这个项目成为可能的开源依赖
- 感谢社区持续的反馈和支持

---

⭐ 如果你觉得这个项目有帮助，请考虑给它一个星星！