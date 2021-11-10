package com.offcn.content.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author chenxi
 * @date 2021/11/8 19:11
 * @description
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private ApiInfo getApiInfo(){
        return new ApiInfoBuilder()
                .title("广告微服务接口文档")
                .description("服务接口文档")
                .version("1.0")
                .termsOfServiceUrl("http://www.content.com")
                .build();
    }

    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .groupName("content")
                .select().build();
    }

}
