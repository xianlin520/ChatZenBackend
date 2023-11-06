# 使用官方JDK 17基础镜像
FROM openjdk:17-jdk-slim
MAINTAINER itfeng<15653674386@163.com>
ADD target/ChatZenBackend-0.0.1-SNAPSHOT.jar app.jar
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
CMD java -jar app.jar