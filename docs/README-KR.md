# Chat-Zen-Spring

[![License](https://img.shields.io/github/license/Chat-Zen/chat-zen-spring)](LICENSE)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.3-green)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-JDK%2021-red)](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)](https://www.mysql.com/)

Spring Boot 3.3 및 JDK 21을 기반으로 구축된 현대적이고 고성능의 채팅 애플리케이션 백엔드입니다. 강력한 보안, 확장성 및 성능 최적화를 갖춘 실시간 통신을 제공하도록 설계되었습니다.

## 🚀 주요 기능

- **실시간 통신**: WebSocket 기반의 인스턴트 메시징
- **사용자 인증 및 권한 부여**: JWT 토큰을 사용한 Spring Security
- **데이터 지속성**: 안정적인 데이터 저장을 위한 MySQL 8.0
- **고성능**: 응답 시간을 최적화하는 Redis 7.0 캐싱
- **비동기 처리**: 백그라운드 작업을 처리하기 위한 RabbitMQ
- **RESTful API**: 모든 서비스를 위한 표준 REST 인터페이스
- **확장 가능한 아키텍처**: 수평 확장을 위한 설계

## 🛠️ 기술 스택

### 백엔드 프레임워크
- **[Spring Boot 3.3.3](https://spring.io/projects/spring-boot)**: 애플리케이션 프레임워크
- **[Java JDK 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)**: 런타임 환경
- **[Spring Security](https://spring.io/projects/spring-security)**: 인증 및 권한 부여
- **[Spring Data JPA](https://spring.io/projects/spring-data-jpa)**: 데이터 액세스 계층
- **[MyBatis-Plus](https://baomidou.com/)**: 향상된 MyBatis 프레임워크
- **[WebSocket](https://docs.spring.io/spring-framework/reference/web/websocket.html)**: 실시간 통신
- **[Lombok](https://projectlombok.org/)**: 코드 생성

### 데이터베이스
- **[MySQL 8.0](https://www.mysql.com/)**: 사용자 데이터 및 채팅 기록을 위한 주 데이터베이스
- **[Redis 7.0](https://redis.io/)**: 고성능 캐싱 및 세션 관리

### 미들웨어
- **[RabbitMQ 3](https://www.rabbitmq.com/)**: 메시지 큐잉 시스템
- **[Logback](https://logback.qos.ch/)**: 로깅 프레임워크

### 기타 종속성
- **[JWT](https://jwt.io/)**: 토큰 기반 인증
- **[Fastjson2](https://github.com/alibaba/fastjson2)**: JSON 처리
- **[Flyway](https://flywaydb.org/)**: 데이터베이스 마이그레이션
- **[Hutool](https://hutool.cn/)**: Java 도구 모음

## 📋 시스템 요구 사항

시작하기 전에 다음이 설치되어 있는지 확인하십시오:

- **[Java JDK 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)** 이상
- **[Maven 3.6](https://maven.apache.org/)** 이상
- **[MySQL 8.0](https://www.mysql.com/)** 데이터베이스 서버
- **[Redis 7.0](https://redis.io/)** 서버
- **[RabbitMQ 3](https://www.rabbitmq.com/)** 메시지 브로커

## 🚀 시작하기

### 1. 저장소 복제

```bash
git clone https://github.com/your-username/chat-zen-spring.git
cd chat-zen-spring
```

### 2. 데이터베이스 설정

1. MySQL 데이터베이스 생성:
```sql
CREATE DATABASE chat_zen_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. 데이터베이스 사용자 생성 및 권한 부여:
```sql
CREATE USER 'chat_zen_user'@'localhost' IDENTIFIED BY 'strong_password';
GRANT ALL PRIVILEGES ON chat_zen_db.* TO 'chat_zen_user'@'localhost';
FLUSH PRIVILEGES;
```

### 3. 환경 구성

1. `src/main/resources/application-dev.yml`에서 데이터베이스 연결 정보 업데이트:

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
    # password: your_redis_password # 필요한 경우

  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
```

### 4. 프로젝트 빌드

```bash
mvn clean compile
```

### 5. 애플리케이션 실행

```bash
# Maven 사용
mvn spring-boot:run

# 또는 JAR 빌드 후 실행
mvn clean package
java -jar target/chat-zen-spring-0.0.1-SNAPSHOT.jar
```

## 🔧 구성

애플리케이션은 여러 환경을 지원합니다:

- **개발**: `application-dev.yml`
- **운영**: `application-prod.yml`
- **테스트**: `application-test.yml`

특정 프로필로 실행:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

## 📡 API 엔드포인트

애플리케이션은 다음 API 엔드포인트를 제공합니다:

- `/auth/**` - 인증 서비스
- `/user/**` - 사용자 관리 서비스
- `/ws/**` - 실시간 통신을 위한 WebSocket 엔드포인트
- `/http-test/**` - 테스트 엔드포인트

로그인, 등록 및 상태 확인 엔드포인트를 제외한 대부분의 엔드포인트에는 인증이 필요합니다.

## 🧪 테스트

테스트 실행:

```bash
# 모든 테스트 실행
mvn test

# 특정 테스트 클래스 실행
mvn -Dtest=YourTestClass test
```

## 🔒 보안

애플리케이션은 다음 보안 조치를 구현합니다:

- JWT 기반 인증
- Spring Security 구성
- 공개 액세스를 위한 화이트리스트 엔드포인트
- 암호화된 비밀번호 저장
- 속도 제한 보호

## 🚀 배포

운영 배포:

1. 애플리케이션 빌드:
   ```bash
   mvn clean package -Pprod
   ```

2. 서버에 JAR 파일 배포:
   ```bash
   java -jar target/chat-zen-spring-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
   ```

## 🤝 기여

기여를 환영합니다! 기여 방법:

1. 저장소 포크
2. 기능 브랜치 생성 (`git checkout -b feature/AmazingFeature`)
3. 변경사항 커밋 (`git commit -m 'Add some AmazingFeature'`)
4. 브랜치에 푸시 (`git push origin feature/AmazingFeature`)
5. 풀 리퀘스트 오픈

### 개발 지침

- 기존 코드 스타일 따르기
- 새 기능에 대한 테스트 작성
- 동작 변경 사항 문서화
- 제출 전 모든 테스트가 통과하는지 확인

## 📄 라이선스

이 프로젝트는 [LICENSE](LICENSE) 파일에 명시된 조건에 따라 라이선스가 부여됩니다.

## 📞 연락처

- **유지 관리자**: 羡林i
- **이메일**: 2683971783@qq.com
- **CSDN**: [羡林i-CSDN博客](https://blog.csdn.net/Y_xianlin)
- **Bilibili**: [羡林i의 개인 공간](https://space.bilibili.com/521082261)

## 🙏 감사의 말

- 훌륭한 프레임워크를 제공한 Spring Boot 팀에게 감사드립니다
- 이 프로젝트를 가능하게 한 모든 오픈 소스 종속성에 특별한 감사를 드립니다
- 지속적인 피드백과 지원을 해준 커뮤니티에 감사드립니다

---

## 🌐 Other Languages

- [English](../README.md)
- [中文文档](README-CN.md)
- [Русский](README-RU.md)

---

## 🌐 Other Languages

- [English](../README.md)
- [中文文档](README-CN.md)
- [Русский](README-RU.md)

---

⭐ 이 프로젝트가 도움이 되었다면, 별을 주는 것을 고려해보세요!