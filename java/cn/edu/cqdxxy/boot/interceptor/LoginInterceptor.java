package cn.edu.cqdxxy.boot.interceptor;

import cn.edu.cqdxxy.boot.entity.Employee;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object user =request.getSession().getAttribute("user");
        if(user!=null){
            //登录过则放行
            return true;
        }
        //未登录则跳转到登录页面
        response.sendRedirect("/sparrow/toLogin");
        return false;
    }
}
