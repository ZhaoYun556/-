package cn.edu.cqdxxy.boot.controller;

import cn.edu.cqdxxy.boot.entity.Dept;
import cn.edu.cqdxxy.boot.entity.Employee;
import cn.edu.cqdxxy.boot.service.DeptService;
import com.github.pagehelper.PageInfo;
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
import java.util.List;

@Controller
public class DeptController {
    @Autowired
    DeptService deptService;
    /*
      (跳转部门页面)查询到所有部门存入域中，并跳转到显示部门的页面
      侧边栏点击
     */
    @GetMapping("/toDept")
    public String toDept(Model model){
        List<Dept> depts=deptService.findAllDept();
        model.addAttribute("depts",depts);
        return "dept/dept";
    }
    /*
    (跳转展示部门细节页面)根据部门页面传递过来的id查询到指定部门，
    然后单个部门存入域中，根据部门id查询这个部门有哪些人,存入域中。
     */
    @GetMapping("/deptDetail")
    public String deptDetail(@RequestParam("id") Integer id,Model model,
                             @RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "5") Integer pageSize){
        //为了动态在页面展示出员工是哪个部门，不需要th：if判断
        List<Dept> allDept = deptService.findAllDept();
        model.addAttribute("allDept",allDept);

        Dept dept=deptService.findDeptById(id);
        model.addAttribute("dept",dept);
        PageInfo<Employee> deptPageInfo =deptService.deptDetailPage(page,pageSize,id);
        /*List<Employee> emps = deptService.findEmpsByDeptId(deptId);*/
        model.addAttribute("deptPageInfo",deptPageInfo);
        model.addAttribute("pageSize",pageSize);
        return "dept/deptDetail";
    }
    /*
      1.1(页面跳转)跳转到添加部门页面
     */
    @GetMapping("/toAddDept")
    public String toAddDept(Model model){


        return "dept/addDept";
    }
    /*
      1.2(添加部门)处理添加部门请求
     */
    @PostMapping("/addDept")
    public String addDept(@RequestParam("imageFile")MultipartFile imageFile,
                        String deptName, Model model
                        ) throws IOException {
        //非空校验
        if(deptName==null||"".equals(deptName.trim())){
            model.addAttribute("deptNameNull","部门名称不能为空!");
            return "dept/addDept";
        }
        //部门名字是否重复
        List<Dept> allDept = deptService.findAllDept();
        for (Dept dept : allDept) {
            if(dept.getName().equals(deptName.trim())){
                model.addAttribute("deptNameError","部门名称重复!");
                return "dept/addDept";
            }
        }

        //获取传过来的文件名字
        String originalFilename=null;
        String imagePath;
        if(imageFile==null||imageFile.getSize()==0||"".equals(imageFile.getOriginalFilename())){
            //没有上传图片
            imagePath=null;
        }else {
            //到这就说明originalFilename符合要求
            originalFilename=imageFile.getOriginalFilename();
            imagePath="/images/deptIcon/"+originalFilename;//存储在数据库的图片地址
            // 获取项目根目录路径
            String projectPath = System.getProperty("user.dir");
            // 拼接相对路径
            String relativePath = projectPath + "/sparrow/src/main/resources/static/images/deptIcon/" + originalFilename;
            // 保存文件
            imageFile.transferTo(new File(relativePath));
            //imageFile.transferTo(new File("E:\\mali_java123\\finalDesign\\sparrow\\src\\main\\resources\\static\\images\\deptIcon\\" + originalFilename));
        }
        deptService.addDept(deptName,imagePath,originalFilename);
        System.out.println("添加部门成功");
        return "redirect:addDeptSuccess";
    }
    /*
      1.3(添加部门成功)提示成功，然后跳转到部门展示页
     */
    @GetMapping("/addDeptSuccess")
        public void addDeptSuccess(HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");//location.href='/sparrow/toAddDept'
        resp.getWriter().write("<script>alert('部门添加成功!');location.href='/sparrow/toDept'</script>");
    }
    /*
      1.1(根据部门id删除部门)
     */
    @GetMapping("/deptDelete")
        public String deptDelete(Integer id) {
        System.out.println("删除部门控制器进入");
        deptService.deleteDeptById(id);
        /*删除部门成功*/
        return "redirect:toDept";
    }

    /*
      1.1(视图跳转)根据部门id修改部门
     */
    @GetMapping("/toDeptUpdate")
    public String toDeptUpdate(Integer id,Model model) {
        Dept deptById = deptService.findDeptById(id);
        model.addAttribute("deptById",deptById);
        /*视图跳转*/
        System.out.println("视图跳转了");
        return "dept/updateDept";
    }
    /*
      1.2(根据部门id修改部门)
     */
    @PostMapping("/deptUpdate")
        public String deptUpdate(Integer id, String deptName,
                                 MultipartFile imageFile,
                                 HttpSession session) throws IOException {
        System.out.println("修改部门控制器进入");
        session.setAttribute("deptNameNull",null);
        session.setAttribute("deptNameError",null);
        //非空校验
        if(deptName==null||"".equals(deptName.trim())){
            session.setAttribute("deptNameNull","部门名称不能为空!");
            return "redirect:toDeptUpdate?id="+id;
        }
        //名字是否重复
        List<Dept> allDept = deptService.findAllDept();
        for (Dept dept : allDept) {
            if(deptName.trim().equals(dept.getName())&&!dept.getId().equals(id)){
                session.setAttribute("deptNameError","部门名称不能重复!");
                return "redirect:toDeptUpdate?id="+id;
            }
        }

        //获取传过来的文件名字
        String imageName=null;//(imageName)
        String imagePath=null;
        if(imageFile!=null&&imageFile.getSize()!=0||!"".equals(imageFile.getOriginalFilename())){
            //到这就说明originalFilename符合要求
            imageName=imageFile.getOriginalFilename();//获取文件名字
            imagePath="/images/deptIcon/"+imageName;//存储在数据库的图片地址
            // 获取项目根目录路径
            String projectPath = System.getProperty("user.dir");
            // 拼接相对路径
            String relativePath = projectPath + "/sparrow/src/main/resources/static/images/deptIcon/" + imageName;
            // 保存文件
            imageFile.transferTo(new File(relativePath));
            //imageFile.transferTo(new File("E:\\mali_java123\\finalDesign\\sparrow\\src\\main\\resources\\static\\images\\deptIcon\\" + imageName));
        }
        deptService.updateDeptById(id,deptName.trim(),imagePath,imageName);
        System.out.println("修改部门成功");
        /*修改部门成功*/
        return "redirect:toDept";
    }
































}
