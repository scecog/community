package com.sceon.community.interceptor;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/8 15:19
 */
@Configuration
@Component
public class WebConfig implements WebMvcConfigurer {
    /*
     * 解决拦截器无法使用@Autowired的问题，拦截器的加载是在Springcontext之前进行的，所以在注入实体就为空
     */
    @Bean
    public SessionInterceptor sessionInterceptor(){
        return new SessionInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor()).addPathPatterns("/**");

    }
}