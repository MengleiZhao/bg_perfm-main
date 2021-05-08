package com.common.system.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by Mr.Yangxiufeng on 2017/6/26.
 * Time:18:05
 * ProjectName:bg_perfm
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(Predicates.or(RequestHandlerSelectors.basePackage("com.common.system.sys.web"),
                        RequestHandlerSelectors.basePackage("com.common.business.index.web"),
                        RequestHandlerSelectors.basePackage("com.common.business.collection.meanslistcheck.web"),
                        RequestHandlerSelectors.basePackage("com.common.business.project.approval.web"),
                        RequestHandlerSelectors.basePackage("com.common.business.collection.means.web"),
                        RequestHandlerSelectors.withClassAnnotation(RestController.class)))


                // .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                //.apis(RequestHandlerSelectors.basePackage("com.common.system.sys.web"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("绩效管理平台")
                .description("绩效管理平台接口说明文档")
                .termsOfServiceUrl("localhost:9000")
                .version("1.0")
                .build();
    }
}
