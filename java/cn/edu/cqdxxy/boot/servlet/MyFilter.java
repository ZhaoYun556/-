package cn.edu.cqdxxy.boot.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
//@WebFilter(urlPatterns = "/MYSERVLET")
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("MyFilterWorking...");
        filterChain.doFilter(servletRequest,servletResponse);//permit through
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
