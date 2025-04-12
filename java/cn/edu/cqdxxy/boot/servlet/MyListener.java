package cn.edu.cqdxxy.boot.servlet;

import ch.qos.logback.classic.util.ContextInitializer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

//ServletContextListener对当前整个web应用进行监听，监听点：
//应用启动时(初始化)和销毁时
//@WebListener
public class MyListener implements ServletContextListener {
    //initialized
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("initialized...");
    }
}
