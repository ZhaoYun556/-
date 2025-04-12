package cn.edu.cqdxxy.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
//@ServletComponentScan("cn.edu.cqdxxy.boot.servlet")
public class SparrowApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SparrowApplication.class, args);
        /*for (String beanDefinitionName : run.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }*/
    }
    /*
    * 如何在springboot项目中使用原生的java web三大组件
    * java web三大组件：Servlet Filter Listener
    * 1.创建类继承HttpServlet，然后重写doGet和doPost方法
    * 在类上添加@WebServlet注解参数(urlPatterns)配置当前servlet对外访问路径
    * 2.
    * */

}
