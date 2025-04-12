package cn.edu.cqdxxy.boot.controller;

import cn.edu.cqdxxy.boot.entity.Admin;
import cn.edu.cqdxxy.boot.entity.ConditionQueryAdmin;
import cn.edu.cqdxxy.boot.service.AdminService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;
    //查询管理员查询管理员查询管理员查询管理员查询管理员查询管理员查询管理员查询管理员查询管理员查询管理员查询管理员查询管理员
    @GetMapping("/adminCRUD")
    public String adminCRUD(@RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "5") Integer pageSize,
                            String username, Integer id,
                            Model model){
        //获取当前选择每页分多少页数据
        model.addAttribute("pageSize",pageSize);
        //接收条件查询的参数，记录下来然后转发给页面用于在刷新页面后输入框内还是有查询的条件
        ConditionQueryAdmin conditionQueryAdmin = new ConditionQueryAdmin(id,username);
        System.out.println("conditionQueryAdmin="+conditionQueryAdmin);
        model.addAttribute("conditionQueryAdmin",conditionQueryAdmin);
        PageInfo<Admin> pageInfo = adminService.pageAdmins(page, pageSize, id,username);
        model.addAttribute("pageInfo",pageInfo);
        return "admins/adminCRUD";
    }
    //查询管理员查询管理员查询管理员查询管理员查询管理员查询管理员查询管理员查询管理员查询管理员查询管理员查询管理员查询管理员


    //添加管理员添加管理员添加管理员添加管理员添加管理员添加管理员添加管理员添加管理员添加管理员添加管理员添加管理员添加管理员
    //1.1 (跳转到添加管理员页面)添加按钮使用
    @GetMapping("/toAddAdmin")
    public String toAddAdmin(){
        return "admins/addAdmin";
    }
    //1.2 (添加管理员请求处理)
    @PostMapping("/addAdmin")
    public String addAdmin(@RequestParam("imageFile")MultipartFile imageFile,
                           String username,String password,
                           Model model) throws IOException {
        //非空校验
        if(username==null || "".equals(username.trim())){
            model.addAttribute("usernameNull","用户名不能为空");
            return "admins/addAdmin";
        }
        if(password==null || "".equals(password.trim())){
            model.addAttribute("passwordNull","密码不能为空");
            return "admins/addAdmin";
        }
        //用户名不能重复
        List<Admin> adminList = adminService.findAllAdmin();
        for (Admin admin : adminList) {
            if(username.trim().equals(admin.getUsername())){
                model.addAttribute("usernameExist","用户名已存在");
                return "admins/addAdmin";
            }
        }
        //判断有没有传头像
        String imageName=null;
        String imagePath=null;
        if(imageFile!=null && imageFile.getSize()!=0 &&!imageFile.getOriginalFilename().equals("")){
            imageName=imageFile.getOriginalFilename();
            imagePath="/images/adminIcon/"+imageName;
            // 获取项目根目录路径
            String projectPath = System.getProperty("user.dir");
            // 拼接相对路径
            String relativePath = projectPath + "/sparrow/src/main/resources/static/images/adminIcon/" + imageName;
            // 保存文件
            imageFile.transferTo(new File(relativePath));
            //imageFile.transferTo(new File("E:\\mali_java123\\finalDesign\\sparrow\\src\\main\\resources\\static\\images\\adminIcon\\" + imageName));
        }
        adminService.addAdmin(username.trim(),password.trim(),imageName,imagePath);
        System.out.println("添加admin成功!");
        return "redirect:adminCRUD";
    }
    //添加管理员添加管理员添加管理员添加管理员添加管理员添加管理员添加管理员添加管理员添加管理员添加管理员添加管理员添加管理员


    //更新管理员信息更新管理员信息更新管理员信息更新管理员信息更新管理员信息更新管理员信息更新管理员信息更新管理员信息
    //1.1 (跳转到更新管理员信息页面)
    @GetMapping("/toUpdateAdmin")
    public String toUpdateAdmin(Integer id,Model model){
        Admin admin = adminService.findAdminById(id);
        model.addAttribute("admin",admin);
        return "admins/updateAdmin";
    }
    //1.2 (更新管理员信息请求处理)
    @PostMapping("/updateAdmin")
    public String updateAdmin(Admin admin, Model model,
                              @RequestParam("imageFile") MultipartFile imageFile,
                              HttpServletResponse resp)
            throws IOException {
        //非空校验
        if(admin.getUsername()==null || "".equals(admin.getUsername().trim())){
            model.addAttribute("usernameNull","用户名不能为空");
            Admin admin1 = adminService.findAdminById(admin.getIdAdmin());
            model.addAttribute("admin",admin1);
            return "admins/updateAdmin";
        }
        if(admin.getPassword()==null || "".equals(admin.getPassword().trim())){
            model.addAttribute("passwordNull","密码不能为空");
            Admin admin1 = adminService.findAdminById(admin.getIdAdmin());
            model.addAttribute("admin",admin1);
            return "admins/updateAdmin";
        }
        //用户名不能重复
        List<Admin> allAdmin = adminService.findAllAdmin();
        for (Admin a : allAdmin) {
            if(admin.getUsername().trim().equals(a.getUsername()) ){
                Admin admin1 = adminService.findAdminById(admin.getIdAdmin());
                model.addAttribute("admin",admin1);
                model.addAttribute("usernameExist","用户名已存在");
                return "admins/updateAdmin";
            }
        }
        //判断有没有传头像
        String imageName = null;
        String imagePath = null;
        if(imageFile!=null && imageFile.getSize()!=0 &&!imageFile.getOriginalFilename().equals("")) {
            imageName = imageFile.getOriginalFilename();
            imagePath = "/images/adminIcon/" + imageName;
            // 获取项目根目录路径
            String projectPath = System.getProperty("user.dir");
            // 拼接相对路径
            String relativePath = projectPath + "/sparrow/src/main/resources/static/images/adminIcon/" + imageName;
            // 保存文件
            imageFile.transferTo(new File(relativePath));
        }
        admin.setImageName(imageName);
        admin.setImagePath(imagePath);
        Date date = new Date();
        Timestamp updateTime = new Timestamp(date.getTime());
        admin.setUpdateTime(updateTime);
        adminService.updateAdmin(admin);
        System.out.println("修改成工");
        //校验通过可以修改信息
        return "redirect:updateAdminTip";
    }
    @GetMapping("/updateAdminTip")
    public void updateAdminTip(HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write("<script>alert('修改成功!');history.back();</script>");
    }
    //更新管理员信息更新管理员信息更新管理员信息更新管理员信息更新管理员信息更新管理员信息更新管理员信息更新管理员信息


    //删除管理员信息删除管理员信息删除管理员信息删除管理员信息删除管理员信息删除管理员信息删除管理员信息删除管理员信息
    //1.1 (删除管理员请求处理)
    @GetMapping("/deleteAdmin")
    public String deleteAdmin(Integer id){
        adminService.deleteAdminById(id);
        return "redirect:adminCRUD";
    }
    //1.2 (批量删除管理员请求处理)
    @GetMapping("/deleteAdmins")
    public String deleteAdmins(@RequestParam("ids")String ids){
        String[] split = ids.split(",");
        adminService.deleteAdminsById(split);
        return "redirect:adminCRUD";
    }

    //删除管理员信息删除管理员信息删除管理员信息删除管理员信息删除管理员信息删除管理员信息删除管理员信息删除管理员信息



















}
