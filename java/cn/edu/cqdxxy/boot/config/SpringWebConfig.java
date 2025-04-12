package cn.edu.cqdxxy.boot.config;

import cn.edu.cqdxxy.boot.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class SpringWebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //1.注册自定义拦截器
        registry.addInterceptor(new LoginInterceptor())
                //2.添加需要被拦截的请求
                .addPathPatterns("/**")
                //3.将不需要拦的排出去
                .excludePathPatterns("/toLogin","/login","/createImage","/css/**","/fonts/**","/images/**","/images/**/**","/js/**","/styles/**","/element-ui/**");
    }

}
