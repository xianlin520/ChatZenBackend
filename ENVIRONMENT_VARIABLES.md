# Chat-Zen-Spring 环境变量配置说明

本文档说明了 Chat-Zen-Spring 项目需要的环境变量及其用途。

## 必需环境变量

以下环境变量在特定环境下是必需的：

### 数据库相关
- `MYSQL_PASSWORD` - MySQL数据库密码
- `DB_HOST` (仅测试环境) - 数据库主机地址

### Redis相关
- `REDIS_PASSWORD` - Redis数据库密码

## 可选环境变量

以下环境变量有默认值，可根据需要覆盖：

### 服务器配置
- `SERVER_PORT` - 服务器端口
  - 默认值：`8080`

### JWT配置
- `CHATZEN_JWT_KEY` - JWT加密密钥
  - 默认值：`chatzen-key`
- `CHATZEN_JWT_EXPIRATION` - JWT过期时间（毫秒）
  - 默认值：`172800000`（48小时）

### 邮件服务配置
- `MAIL_HOST` - 邮件服务器地址
  - 默认值：`smtp.163.com`
- `MAIL_PORT` - 邮件服务器端口
  - 默认值：`465`
- `MAIL_USERNAME` - 邮件服务器用户名
  - 默认值：`QianXun_XianLin@163.com`
- `MAIL_PASSWORD` - 邮件服务器密码
  - 默认值：`LQu3M38vgEXJiVaE`
- `MAIL_SSL_ENABLE` - 是否启用SSL
  - 默认值：`true`
- `MAIL_SSL_REQUIRED` - 是否要求SSL
  - 默认值：`true`
- `MAIL_AUTH` - 是否启用身份验证
  - 默认值：`true`

### 雪花算法配置
- `SNOWFLAKE_WORKER_ID` - 雪花算法工作ID
  - 默认值：`1`
- `SNOWFLAKE_DATACENTER_ID` - 雪花算法数据中心ID
  - 默认值：`1`

## 使用示例

### 开发环境
```bash
# 服务器配置
export SERVER_PORT=8899

# JWT配置
export CHATZEN_JWT_KEY=your_development_secret_key

# 数据库配置
export MYSQL_PASSWORD=root
export REDIS_PASSWORD=redis_xlpass

# 邮件配置（可选）
export MAIL_HOST=smtp.gmail.com
export MAIL_PORT=587
export MAIL_USERNAME=your_dev_email@gmail.com
export MAIL_PASSWORD=your_dev_app_password
export MAIL_SSL_ENABLE=false
```

### 生产环境
```bash
# 服务器配置
export SERVER_PORT=8080

# JWT配置（必需使用强密钥）
export CHATZEN_JWT_KEY=your_strong_production_secret_key

# 数据库配置（必需）
export MYSQL_PASSWORD=your_secure_mysql_password
export REDIS_PASSWORD=your_secure_redis_password

# 邮件配置
export MAIL_HOST=smtp.yourdomain.com
export MAIL_USERNAME=service@yourdomain.com
export MAIL_PASSWORD=your_secure_mail_password
```

## 注意事项

1. **生产环境中的安全**：在生产环境中，务必使用强密钥作为 `CHATZEN_JWT_KEY` 的值
2. **邮件服务配置**：根据实际使用的邮件服务商调整SMTP设置
3. **雪花算法ID**：在多实例部署时，需要为不同的实例设置不同的worker-id和datacenter-id以确保ID唯一性
4. **数据库密码**：在生产环境中，不要使用简单的密码如 "root"