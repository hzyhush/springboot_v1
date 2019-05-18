package com.unicom.api.cterminal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 配置swagger
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.unicom.api.cterminal.controller"))
                .paths(PathSelectors.any())//路径判断,这里是任何路径
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //自定义信息可按需求填写
                .title("Spring Boot中使用Swagger构建RESTful API")
                .description("简单优雅的restfun风格")
                .termsOfServiceUrl("http://www.duanxiaowei.top")
                .version("1.0")
                .build();
    }
}

