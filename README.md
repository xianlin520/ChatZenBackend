# Chat-Zen-Spring

[![License](https://img.shields.io/github/license/Chat-Zen/chat-zen-spring)](LICENSE)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.3-green)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-JDK%2021-red)](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)](https://www.mysql.com/)

A modern, high-performance chat application backend built with Spring Boot 3.3 and JDK 21. Designed to provide real-time communication with robust security, scalability, and performance optimization.

## 🚀 Features

- **Real-time Communication**: WebSocket-based instant messaging
- **User Authentication & Authorization**: Spring Security with JWT tokens
- **Data Persistence**: MySQL 8.0 for reliable data storage
- **High Performance**: Redis 7.0 caching for optimized response times
- **Asynchronous Processing**: RabbitMQ for handling background tasks
- **RESTful APIs**: Standard REST interfaces for all services
- **Scalable Architecture**: Designed for horizontal scaling

## 🛠️ Tech Stack

### Backend Framework
- **[Spring Boot 3.3.3](https://spring.io/projects/spring-boot)**: Application framework
- **[Java JDK 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)**: Runtime environment
- **[Spring Security](https://spring.io/projects/spring-security)**: Authentication and authorization
- **[Spring Data JPA](https://spring.io/projects/spring-data-jpa)**: Data access layer
- **[MyBatis-Plus](https://baomidou.com/)**: Enhanced MyBatis framework
- **[WebSocket](https://docs.spring.io/spring-framework/reference/web/websocket.html)**: Real-time communication
- **[Lombok](https://projectlombok.org/)**: Code generation

### Databases
- **[MySQL 8.0](https://www.mysql.com/)**: Primary database for user data and chat history
- **[Redis 7.0](https://redis.io/)**: High-performance caching and session management

### Middleware
- **[RabbitMQ 3](https://www.rabbitmq.com/)**: Message queuing system
- **[Logback](https://logback.qos.ch/)**: Logging framework

### Other Dependencies
- **[JWT](https://jwt.io/)**: Token-based authentication
- **[Fastjson2](https://github.com/alibaba/fastjson2)**: JSON processing
- **[Flyway](https://flywaydb.org/)**: Database migration
- **[Hutool](https://hutool.cn/)**: A set of Java tools

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

- **[Java JDK 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)** or higher
- **[Maven 3.6](https://maven.apache.org/)** or higher
- **[MySQL 8.0](https://www.mysql.com/)** database server
- **[Redis 7.0](https://redis.io/)** server
- **[RabbitMQ 3](https://www.rabbitmq.com/)** message broker

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/chat-zen-spring.git
cd chat-zen-spring
```

### 2. Database Setup

1. Create a MySQL database:
```sql
CREATE DATABASE chat_zen_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. Create a database user and grant permissions:
```sql
CREATE USER 'chat_zen_user'@'localhost' IDENTIFIED BY 'strong_password';
GRANT ALL PRIVILEGES ON chat_zen_db.* TO 'chat_zen_user'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Configure Environment

1. Update database connection details in `src/main/resources/application-dev.yml`:

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
    # password: your_redis_password # if applicable

  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
```

### 4. Build the Project

```bash
mvn clean compile
```

### 5. Run the Application

```bash
# Using Maven
mvn spring-boot:run

# Or build and run the JAR
mvn clean package
java -jar target/chat-zen-spring-0.0.1-SNAPSHOT.jar
```

## 🔧 Configuration

The application supports multiple environments:

- **Development**: `application-dev.yml`
- **Production**: `application-prod.yml`
- **Test**: `application-test.yml`

To run with a specific profile:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

## 📡 API Endpoints

The application provides the following API endpoints:

- `/auth/**` - Authentication services
- `/user/**` - User management services
- `/ws/**` - WebSocket endpoints for real-time communication
- `/http-test/**` - Testing endpoints

Authentication is required for most endpoints except for login, registration, and health check endpoints.

## 🧪 Testing

To run the tests:

```bash
# Run all tests
mvn test

# Run specific test class
mvn -Dtest=YourTestClass test
```

## 🔒 Security

The application implements security measures including:

- JWT-based authentication
- Spring Security configuration
- Whitelisted endpoints for public access
- Encrypted password storage
- Rate limiting protection

## 🚀 Deployment

For production deployment:

1. Build the application:
   ```bash
   mvn clean package -Pprod
   ```

2. Deploy the JAR file to your server:
   ```bash
   java -jar target/chat-zen-spring-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
   ```

## 🤝 Contributing

Contributions are welcome! Here's how you can contribute:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Development Guidelines

- Follow the existing code style
- Write tests for new features
- Document any changes in behavior
- Make sure all tests pass before submitting

## 📄 License

This project is licensed under the terms specified in the [LICENSE](LICENSE) file.

## 📞 Contact

- **Maintainer**: 羡林i
- **Email**: 2683971783@qq.com
- **CSDN**: [羡林i-CSDN博客](https://blog.csdn.net/Y_xianlin)
- **Bilibili**: [羡林i的个人空间](https://space.bilibili.com/521082261)

## 🙏 Acknowledgments

- Thanks to the Spring Boot team for the excellent framework
- Special thanks to all open-source dependencies that made this project possible
- Appreciation to the community for continuous feedback and support

---

## 🌐 其他语言

- [中文文档](README-CN.md)

---

⭐ If you find this project helpful, consider giving it a star!