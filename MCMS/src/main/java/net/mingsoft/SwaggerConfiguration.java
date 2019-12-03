package net.mingsoft;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean()
    @Order(value = 1)
    public Docket platformApi() {
        List<Parameter> parameters= Lists.newArrayList();

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("1、后台接口")
                .select()
                .apis(basePackage("net.mingsoft.cms.action"))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(parameters)

                ;

    }


    public static Predicate<RequestHandler> basePackage(final String basePackage) {
        return input -> declaringClass(input).transform(handlerPackage(basePackage)).or(true);
    }

    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage)     {
        return input -> {
            // 循环判断匹配
            for (String strPackage : basePackage.split(";")) {
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.fromNullable(input.declaringClass());
    }

    //Swagger中过滤掉任意API接口的方法 TODO 不想研究了
    //http://www.daxiblog.com/2018/07/25/swagger%E4%B8%AD%E8%BF%87%E6%BB%A4%E6%8E%89%E4%BB%BB%E6%84%8Fapi%E6%8E%A5%E5%8F%A3%E7%9A%84%E6%96%B9%E6%B3%95/

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(" 后台接口文档 RESTful APIs")
                .description("  ^_^  ")
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }
}
