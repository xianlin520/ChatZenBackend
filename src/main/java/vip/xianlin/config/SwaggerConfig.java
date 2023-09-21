package vip.xianlin.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.service.contexts.SecurityContext;

import java.util.List;

@Configuration
public class SwaggerConfig {
    
    /**
     * 配置文档介绍以及详细信息
     *
     * @return OpenAPI
     */
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("Authorization", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                        )
                )
                .info(new Info().title("轻语阁API文档")
                        .description("欢迎来到轻语阁-ChatZen的API文档, 本文档由Swagger3.0.0自动生成")
                        // 设置作者
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("羡林i")
                                .email("2683971783@qq.com")
                        )
                        .version("1.0")
                        .license(new License()
                                .name("项目开源地址")
                                .url("https://gitee.com/xianlin-i/chat-zen-backend")
                        )
                )
                .externalDocs(new ExternalDocumentation()
                        .description("我们的官方网站")
                        .url("https://xianlin.vip")
                );
    }
    
    private SecurityContext tokenContext() {
        return SecurityContext.builder()
                .securityReferences(List.of(SecurityReference.builder()
                        .scopes(new AuthorizationScope[0])
                        .reference("Authorization")
                        .build()))
                .build();
    }
}
