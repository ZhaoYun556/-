package cn.edu.cqdxxy.boot.controller;

import cn.edu.cqdxxy.boot.entity.Admin;
import cn.edu.cqdxxy.boot.entity.Employee;
import cn.edu.cqdxxy.boot.service.AdminService;
import cn.edu.cqdxxy.boot.service.EmpService;
import cn.edu.cqdxxy.boot.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.net.HttpCookie;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class LoginController {
    @Autowired
    EmpService empService;
    @Autowired
    AdminService adminService;
    /**
     * 获取登录页面
     * @return
     */
    @GetMapping("/toLogin")
    public String toLogin() {
        System.out.println("toLogin请求发出,跳转到登录页(login.html)");
        return "login/login";
    }

    /**
     * 获取验证码图片，由login页面调佣
     * @param resp
     * @throws IOException
     */
    @GetMapping("/createImage")
    public void createImage(HttpServletResponse resp, HttpSession session) throws IOException {
        Object[] image = ImageUtil.createImage();
        resp.setContentType("image/png");
        ImageIO.write((RenderedImage) image[1], "png", resp.getOutputStream());
        String validCode=(String)image[0];
        session.setAttribute("validCode",validCode);
        System.out.println("验证码请求处理，验证码为："+validCode);
    }

    /**
     * 登录请求处理
     * @return
     */
    @PostMapping("/login")
    public String login(String username, String password,
                        String validCode, Model model,
                        HttpSession session, HttpServletRequest req,
                        HttpServletResponse resp,String userType) {
        System.out.println("登录请求处理");
        //向浏览器发送当前选择的是什么类型的登录账户.浏览器得到这个变量,根据不同类型展示不同效果.
        model.addAttribute("userType",userType);
        //非空校验
        if(username==null || username.trim().equals("")){
            model.addAttribute("usernameNull","用户名不能为空");
            return "login/login";
        }
        //非空校验
        if(password==null || password.trim().equals("")){
            model.addAttribute("passwordNull","密码不能为空");
            return "login/login";
        }
        //非空校验
        if(validCode==null || validCode.trim().equals("")){
            model.addAttribute("validCodeNull","验证码不能为空");
            return "login/login";
        }
        //验证码校验
        if(!validCode.trim().equalsIgnoreCase((String)session.getAttribute("validCode"))){
            model.addAttribute("validCodeError","验证码错误");
            return "login/login";
        }
        //因为前面已经做过用户名非空校验了，如果它为空, 执行不到这里来就跳转了，
        //所以查之前把空字符串清空，不会导致空指针。
        //判断登录账户类型
        //1.1实现登录时间提示功能
        String usernameForCookie = null;
        String cookieRecorder=null;
        System.out.println("userType="+userType);
        if(userType.equals("管理员")){
            System.out.println("当前是管理员登录");
            //管理员账户登录逻辑
            Admin admin = adminService.findAdminByUsername(username.trim());
            System.out.println("账号信息:"+admin);
            //用户名校验
            if(admin==null){
                model.addAttribute("usernameError","用户名错误");
                return "login/login";
            }
            //密码校验
            if(!password.trim().equals(admin.getPassword())){
                model.addAttribute("passwordError","密码错误");
                return "login/login";
            }
            //校验通过
            //添加当前账号到session，(只有添加了这个才能访问后续的页面)
            session.setAttribute("user",admin);
            session.setAttribute("isAdmin",true);
            //1.1实现登录时间提示功能
            usernameForCookie = admin.getUsername()+"A";
            cookieRecorder=usernameForCookie+"Recoder";
        }else if(userType.equals("普通用户")){
            System.out.println("当前是普通用户登录");
            //普通用户账户登录逻辑
            Employee emp = empService.findEmpByUsername(username.trim());
            System.out.println("账号信息:"+emp);
            //用户名校验
            if(emp==null){
                model.addAttribute("usernameError","用户名错误");
                return "login/login";
            }
            //密码校验
            if(!password.trim().equals(emp.getPassword())){
                model.addAttribute("passwordError","密码错误");
                return "login/login";
            }
            //校验通过
            //添加当前账号到session，(只有添加了这个才能访问后续的页面)
            session.setAttribute("user",emp);
            session.setAttribute("isAdmin",false);
            //1.1实现登录时间提示功能
            usernameForCookie = emp.getUsername()+"U";
            cookieRecorder=usernameForCookie+"Recoder";
        }


        // 创建当前时间，用于后面记录这次的登录时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        String formatdate = simpleDateFormat.format(new Date());
        System.out.println("当前时间："+formatdate);
        boolean flag=false;//记录器，记录是否第一次登录
        //如果前端的cookie不为null则遍历
        if(req.getCookies()!=null && req.getCookies().length>0){
            System.out.println("判断是否第一次登录");
            for (Cookie cookie : req.getCookies()) {
                //一.如果之前添加过usernameForCookie这个Cookie则
                if(usernameForCookie.equals(cookie.getName())){
                    flag=true;//不是第一次登录
                    for (Cookie reqCookie : req.getCookies()) {
                        if(cookieRecorder.equals(reqCookie.getName())){
                            cookie.setValue(reqCookie.getValue());
                            cookie.setPath("/");
                            cookie.setMaxAge(60*60*24*30);/*60*60*24*30=一个月*/
                            resp.addCookie(cookie);
                            //修改完成后记录这次登录时间用于下次提示
                            reqCookie.setValue(formatdate);
                            reqCookie.setPath("/");
                            reqCookie.setMaxAge(60*60*24*30);/*60*60*24*30=一个月*/
                            resp.addCookie(reqCookie);
                            //测试用
                            System.out.println("cookName="+cookie.getName());
                            System.out.println("cookValue，上次登录时间="+cookie.getValue());
                            System.out.println("再次添加cookie成功");
                            System.out.println("登录成功!");
                            return "redirect:toIndex";
                        }
                    }
                }
            }
        }
        System.out.println("首次登录外");
        //1.2之前没有添加过empLastTime这个Cookie
        if(req.getCookies()==null || req.getCookies().length==0 || flag==false){
            System.out.println("首次登录");
            Cookie userLastTime = new Cookie(usernameForCookie, "欢迎首次登录！");
            Cookie userLastTimeRecord = new Cookie(cookieRecorder, formatdate);
            userLastTime.setPath("/");
            userLastTimeRecord.setPath("/");
            //存活一个月
            userLastTime.setMaxAge(60*60*24*30);/*60*60*24*30=一个月*/
            userLastTimeRecord.setMaxAge(60*60*24*30);/*60*60*24*30=一个月*/
            resp.addCookie(userLastTime);
            resp.addCookie(userLastTimeRecord);
            //测试用
            System.out.println("cookName="+userLastTime.getName());
            System.out.println("cookValue="+userLastTime.getValue());
            System.out.println("首次添加cookie成功");
            System.out.println("登录成功!");
            return "redirect:toIndex";
        }
        //貌似永远不会执行的代码...
        System.out.println("登录成功!");
        return "redirect:toIndex";
    }
    @GetMapping("logout")
    public String logout(HttpSession session){
        session.removeAttribute("emp");
        return "redirect:toLogin";
    }

}
