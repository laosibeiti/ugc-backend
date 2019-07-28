package top.justdj.ugc.config;

import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * swagger配置
 *
 * @author xuk
 * @date 2018/6/29
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {


//    @Value("${swagger2.enable}")
//    private boolean swagger2Enable;
    
    //自定义异常信息
    ArrayList<ResponseMessage> responseMessages = new ArrayList<ResponseMessage>() {{
        add(new ResponseMessageBuilder().code(-1).message("操作失败").build());
        add(new ResponseMessageBuilder().code(200).message("操作成功").build());
        add(new ResponseMessageBuilder().code(400).message("请求参数错误").build());
//        add(new ResponseMessageBuilder().code(401).message("权限认证失败").responseModel(new ModelRef("Error")).build());
//        add(new ResponseMessageBuilder().code(403).message("请求资源不可用").responseModel(new ModelRef("Error")).build());
        add(new ResponseMessageBuilder().code(404).message("请求资源不存在").build());
//        add(new ResponseMessageBuilder().code(409).message("请求资源冲突").responseModel(new ModelRef("Error")).build());
//        add(new ResponseMessageBuilder().code(415).message("请求格式错误").responseModel(new ModelRef("Error")).build());
//        add(new ResponseMessageBuilder().code(423).message("请求资源被锁定").responseModel(new ModelRef("Error")).build());
        add(new ResponseMessageBuilder().code(500).message("服务器内部错误").build());
//        add(new ResponseMessageBuilder().code(501).message("请求方法不存在").responseModel(new ModelRef("Error")).build());
//        add(new ResponseMessageBuilder().code(503).message("服务暂时不可用").responseModel(new ModelRef("Error")).build());
//        add(new ResponseMessageBuilder().code(-1).message("未知异常").responseModel(new ModelRef("Error")).build());
    }};
    
    @Bean
    public Docket createDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
//                .enable(swagger2Enable)
                .apiInfo(apiInfo())
                //将时间类型全部转为String类型
                .directModelSubstitute(LocalDateTime.class, String.class)
                //将日期类型全部转为String类型
                .directModelSubstitute(LocalDate.class, String.class)
                .groupName("接口文档")
                .select()
                .apis(RequestHandlerSelectors.basePackage("top.justdj.ugc.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(generateParameters())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET,responseMessages)
                .globalResponseMessage(RequestMethod.POST,responseMessages)
                .globalResponseMessage(RequestMethod.PATCH,responseMessages)
                .globalResponseMessage(RequestMethod.DELETE,responseMessages);
    }

    private List<Parameter> generateParameters() {
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        List<Parameter> parameters = Lists.newArrayList();
        parameterBuilder.name("token").description("token令牌").modelRef(new ModelRef("String"))
                .parameterType("header").defaultValue("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjoie1wiY3JlYXRlVGltZVwiOjE1NTE2NzQ0MDA5OTUsXCJlbWFpbFwiOlwiMjI2OTA5MDAyMEBxcS5jb21cIixcImlkXCI6XCI1Yzc1NTBmNTlkNDcxZTVlZDQyMTFmZDRcIixcIm5hbWVcIjpcIuWtlOWAvOaNp1wiLFwicGFzc3dvcmRcIjpcIjEyMzQ1NlwiLFwicGhvbmVcIjpcIjEzNTg4MjIxOTQ3XCIsXCJyb2xlSWRcIjpbMSwyLDNdLFwic291cmNlXCI6LTF9IiwiZW1haWwiOiIyMjY5MDkwMDIwQHFxLmNvbSIsInBhc3N3b3JkIjoiMTIzNDU2IiwiZXhwIjoxNTUyNTM4NDAxLCJuYmYiOjE1NTE2NzQ0MDF9.73iwUwSUm66t8uXA8Uo-er1CmHGea-vX09-Lxtr6rBs")
                .required(false).build();
        parameters.add(parameterBuilder.build());
        return parameters;
    }

    private Predicate<RequestHandler> predicate() {
        Predicate<RequestHandler> predicate = input -> {
            Class<?> declaringClass = null;
            if (input != null) {
                declaringClass = input.declaringClass();
            }
            // 被注解的类
            assert declaringClass != null;
            return declaringClass.isAnnotationPresent(Api.class);
        };
        return predicate;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(" 校内兼职系统 基础服务API文档")
                .description("job模块的接口")
                .version("1.0")
                .build();
    }
}
