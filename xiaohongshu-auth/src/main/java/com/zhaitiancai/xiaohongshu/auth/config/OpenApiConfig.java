package com.zhaitiancai.xiaohongshu.auth.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
@Bean
public OpenAPI customOpenAPI() {
    return new OpenAPI()
            .info(new Info()
                    .title("小红书认证系统 API")
                    .description("提供认证相关的 RESTful API")
                    .version("1.0.0")  // 添加版本号
                    .termsOfService("http://example.com/terms")
                    .license(new License()
                            .name("Apache 2.0")
                            .url("http://www.apache.org/licenses/LICENSE-2.0.html")
                    )
            )
            .externalDocs(new ExternalDocumentation()
                    .description("项目文档")
                    .url("https://github.com/your-repo/docs")
            );
}

@Bean
public GroupedOpenApi publicApi() {
    return GroupedOpenApi.builder()
            .group("Web 后台接口")
            .pathsToMatch("/test/**")
            .build();
}
}
