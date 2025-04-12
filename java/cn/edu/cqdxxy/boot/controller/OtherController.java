package cn.edu.cqdxxy.boot.controller;

import cn.edu.cqdxxy.boot.entity.Admin;
import cn.edu.cqdxxy.boot.entity.Comment;
import cn.edu.cqdxxy.boot.entity.Employee;
import cn.edu.cqdxxy.boot.service.AdminService;
import cn.edu.cqdxxy.boot.service.CommentService;
import cn.edu.cqdxxy.boot.service.DeptService;
import cn.edu.cqdxxy.boot.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

@Controller
public class OtherController {
    @Autowired
    private EmpService empService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private AdminService adminService;
    //修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码
    /*1.1(获取页面)修改密码。*/
    @GetMapping("/toModifyPwd")
    public String toModifyPwd() {
        return "otherPage/modifyPwd";
    }

    /*1.2(修改密码)*/
    @PostMapping("/modifyPwd")
    public String modifyPwd(Model model, HttpSession session,
                            String oldPassword, String newPassword,
                            String reNewPassword) {
        //非空校验
        if (oldPassword == null || "".equals(oldPassword.trim())) {
            model.addAttribute("oldPasswordNull", "旧密码不能为空");
            return "otherPage/modifyPwd";
        }
        if (newPassword == null || "".equals(newPassword.trim())) {
            model.addAttribute("newPasswordNull", "新密码不能为空");
            return "otherPage/modifyPwd";
        }
        if (reNewPassword == null || "".equals(reNewPassword.trim())) {
            model.addAttribute("reNewPasswordNull", "再次输入密码不能为空");
            return "otherPage/modifyPwd";
        }
        //新旧密码是否相同
        boolean isAdmin = (boolean) session.getAttribute("isAdmin");
        if(isAdmin){
            //管理员修改密码
            Admin admin = (Admin) session.getAttribute("user");
            if (!oldPassword.equals(admin.getPassword())) {
                model.addAttribute("oldPasswordError", "旧密码错误");
                return "otherPage/modifyPwd";
            }
            if (!newPassword.equals(reNewPassword)) {
                model.addAttribute("reNewPasswordError", "新旧密码不相同");
                return "otherPage/modifyPwd";
            }
            //校验通过
            adminService.modifyPwd(admin.getIdAdmin(), newPassword);
        }else {
            //用户修改密码
            Employee emp = (Employee) session.getAttribute("user");
            if (!oldPassword.equals(emp.getPassword())) {
                model.addAttribute("oldPasswordError", "旧密码错误");
                return "otherPage/modifyPwd";
            }
            if (!newPassword.equals(reNewPassword)) {
                model.addAttribute("reNewPasswordError", "新旧密码不相同");
                return "otherPage/modifyPwd";
            }
            //校验通过
            empService.modifyPwd(emp.getIdEmp(), newPassword);
        }
        return "redirect:ModifyPwdSuccess";
    }

    /*1.3(获取页面)修改密码成功提示。*/
    @GetMapping("/ModifyPwdSuccess")
    public void ModifyPwdSuccess(HttpSession session, HttpServletResponse resp) throws IOException {
        session.removeAttribute("user");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write("<script>alert('密码修改成功，点击后重新登录。');location.href='/sparrow/toLogin'</script>");
    }
    //修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码修改密码

    //编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息
    /*1.1(获取页面)编辑个人信息。*/
    @GetMapping("/toModifyInfo")
    public String toModifyInfo(Model model) {
        double random = Math.random();
        model.addAttribute("random", random);
        return "otherPage/modifyInfo";
    }
    /*1.2(编辑个人信息)*/
    @PostMapping("/modifyInfo")
    public String modifyInfo(@RequestParam("imageFile") MultipartFile imageFile,
                             String name, HttpSession session,
                             Model model) throws IOException {
        //非空校验
        if (!(boolean) session.getAttribute("isAdmin")&&name == null && "".equals(name.trim())) {
            System.out.println("姓名不能为空");
            model.addAttribute("nameNull", "姓名不能为空");
            return "otherPage/modifyInfo";
        }
        String imagePath = null;
        String imageName=null;
        //如果imageFile为null则表明没有修改头像，给imagePath=传一个null，数据库表写好逻辑判断，就不会修改原来的头像
        if (imageFile != null && imageFile.getSize() != 0 ||!imageFile.getOriginalFilename().equals("")) {//传了文件
            System.out.println("iamgepath有值了");
            //表明要修改头像
            imageName = imageFile.getOriginalFilename();//传过来的文件名字
            //将文件存入磁盘
            // 获取项目运行时的工作目录（当前工作目录，运行Java程序时所在的目录）
            if((boolean) session.getAttribute("isAdmin")){
                imagePath = "/images/adminIcon/" + imageName;//需要存入数据库的路径地址
                //管理员图片存储路径
                // 获取项目根目录路径
                String projectPath = System.getProperty("user.dir");
                // 拼接相对路径
                String relativePath = projectPath + "/sparrow/src/main/resources/static/images/adminIcon/" + imageName;
                // 保存文件
                imageFile.transferTo(new File(relativePath));
                //imageFile.transferTo(new File("E:\\mali_java123\\finalDesign\\sparrow\\src\\main\\resources\\static\\images\\userIcon\\" + originalFilename));

            }else {
                imagePath = "/images/userIcon/" + imageName;//需要存入数据库的路径地址
                //用户图片存储路径
                // 获取项目根目录路径
                String projectPath = System.getProperty("user.dir");
                // 拼接相对路径
                String relativePath = projectPath + "/sparrow/src/main/resources/static/images/userIcon/" + imageName;
                // 保存文件
                imageFile.transferTo(new File(relativePath));
                //imageFile.transferTo(new File("E:\\mali_java123\\finalDesign\\sparrow\\src\\main\\resources\\static\\images\\userIcon\\" + originalFilename));
            }
        }else if((boolean) session.getAttribute("isAdmin")){
            //如果是管理员账号，则必须上传头像
            model.addAttribute("imageError", "请上传头像");
            return "otherPage/modifyInfo";
        }

        if((boolean) session.getAttribute("isAdmin")){
            //管理员
            Admin admin = (Admin) session.getAttribute("user");
            //System.out.println("modifyInfo方法内:" + emp);
            //校验通过
            System.out.println("imagePath=" + imagePath);
            adminService.modifyInfo(admin.getIdAdmin(),imagePath,imageName);
            //1.1移除旧的，添加新的
            session.removeAttribute("user");
            session.setAttribute("user", adminService.findAdminById(admin.getIdAdmin()));
            System.out.println("移除旧的，添加新的;"+((Admin) session.getAttribute("user")).getImagePath());

        }else {
            //用户
            Employee emp = (Employee) session.getAttribute("user");
            //System.out.println("modifyInfo方法内:" + emp);
            //校验通过
            System.out.println("imagePath=" + imagePath);
            empService.modifyInfo(emp.getIdEmp(),name,imagePath,imageName);
            //1.1移除旧的，添加新的
            session.removeAttribute("user");
            session.setAttribute("user", empService.findEmpById(emp.getIdEmp()));
        }
        return "redirect:modifyInfoSuccess";
    }
    /*1.3(获取页面)修改信息成功提示。*/
    @GetMapping("/modifyInfoSuccess")
    public void modifyInfoSuccess(HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write("<script>alert('修改成功。');history.back()</script>");
    }
    //编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息编辑个人信息








}
