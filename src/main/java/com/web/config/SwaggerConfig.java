package com.web.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("零壹星球 · 智能分类导航站")
                        .version("1.0")
                        .description("分类树管理 + 缓存优化 + 后续接入 AI 生成"));
    }
}
