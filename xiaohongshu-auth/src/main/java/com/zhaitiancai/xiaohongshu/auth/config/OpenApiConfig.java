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
/**
 * 自定义OpenAPI配置
 * 用于配置Swagger文档的元数据信息
 * @return 返回配置好的OpenAPI实例
 */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("小红书认证系统 API")  // API标题
                        .description("提供认证相关的 RESTful API")  // API描述
                        .version("1.0.0")  // 添加版本号
                        .termsOfService("http://example.com/terms")  // 服务条款链接
                        .license(new License()
                                .name("Apache 2.0")  // 许可证名称
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")  // 许可证链接
                        )
                )
                .externalDocs(new ExternalDocumentation()
                        .description("项目文档")  // 外部文档描述
                        .url("https://github.com/your-repo/docs")  // 外部文档链接
                );
    }

/**
 * 配置Swagger的公共API分组
 * 该方法用于创建一个名为"Web 后台接口"的API分组，并指定该分组包含的接口路径
 *
 * @return GroupedOpenApi 返回一个配置好的API分组对象
 */
    @Bean
    public GroupedOpenApi publicApi() {
    // 使用构建器模式创建GroupedOpenApi实例
        return GroupedOpenApi.builder()
            // 设置分组名称为"Web 后台接口"
                .group("Web 后台接口")
            // 指定该分组匹配的路径，包含"/test/**"和"/verification/**"下的所有接口
                .pathsToMatch("/test/**","/verification/**","/user/**")
            // 构建并返回配置完成的GroupedOpenApi实例
                .build();
    }
}
