package cn.edu.cqdxxy.boot.config;

import cn.edu.cqdxxy.boot.servlet.MyFilter;
import cn.edu.cqdxxy.boot.servlet.MyListener;
import cn.edu.cqdxxy.boot.servlet.MyServlet;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Servlet;
import java.lang.reflect.Array;
import java.util.Arrays;

@Configuration
public class JavaWebConfig {
    public JavaWebConfig(){}
    //register Servlet
    @Bean
    public ServletRegistrationBean myServlet(){
        ServletRegistrationBean<Servlet> servletServletRegistrationBean =
                new ServletRegistrationBean<>(new MyServlet(),"/MYSERVLET");
        return servletServletRegistrationBean;
    }
    //register listener
    @Bean
    public ServletListenerRegistrationBean myListener(){
        ServletListenerRegistrationBean servletListenerRegistrationBean =
                new ServletListenerRegistrationBean(new MyListener());
        return servletListenerRegistrationBean;
    }
    //register filter
    @Bean
    public FilterRegistrationBean myFilter(){
        FilterRegistrationBean filterRegistrationBean =
                new FilterRegistrationBean(new MyFilter());
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/MYSERVLET"));
        return filterRegistrationBean;
    }
}
