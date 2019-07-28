package top.justdj.ugc.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shan
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

   /* @Autowired
    private SysSessionInfoArgumentResolver sysSessionInfoArgumentResolver;*/

    /**
     * 参数解析器
     */
    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        //argumentResolvers.add(sysSessionInfoArgumentResolver);
        super.addArgumentResolvers(argumentResolvers);
    }
    
    /**
     * 处理swagger
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("file:/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/img/**").addResourceLocations("file:/home/img/");
        super.addResourceHandlers(registry);
    }
    
    /**
     * 处理跨域
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8000")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowCredentials(true);
    }


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        // 1.需要先定义一个convert 转换消息的对象
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        // 2.添加fastJson的配置信息,比如，是否需要格式化返回的json数据
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        // 空值特别处理
        // WriteNullListAsEmpty 将Collection类型字段的字段空值输出为[]
        // WriteNullStringAsEmpty 将字符串类型字段的空值输出为空字符串 ""
        // WriteNullNumberAsZero 将数值类型字段的空值输出为0
        // WriteNullBooleanAsFalse 将Boolean类型字段的空值输出为false
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.DisableCircularReferenceDetect);
        // 处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);
        // 3.在convert中添加配置信息
        fastConverter.setFastJsonConfig(fastJsonConfig);
        // 4.将convert添加到converters当中
        converters.add(fastConverter);

    }

//    @Bean
//    public SimpleMappingExceptionResolver simpleMappingExceptionResolver(){
//        SimpleMappingExceptionResolver simpleMappingExceptionResolver=new SimpleMappingExceptionResolver();
//        Properties properties=new Properties();
//        properties.put("org.apache.shiro.authz.UnauthorizedException","/login/unAuthorized");
//        properties.put("org.apache.shiro.authz.UnauthenticatedException","/login/checkLogin");
//        simpleMappingExceptionResolver.setExceptionMappings(properties);
//        return simpleMappingExceptionResolver;
//    }
    
    
    /**
     * 配置servlet处理
     */
    @Override
    public void configureDefaultServletHandling(
            DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    
}
