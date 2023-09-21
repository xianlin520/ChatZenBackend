package vip.xianlin;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("vip.xianlin.mapper")
@OpenAPIDefinition
public class ChatZenBackendApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ChatZenBackendApplication.class, args);
    }
    
}
