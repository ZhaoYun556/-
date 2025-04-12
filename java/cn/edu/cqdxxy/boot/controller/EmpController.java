package cn.edu.cqdxxy.boot.controller;

import cn.edu.cqdxxy.boot.entity.*;
import cn.edu.cqdxxy.boot.service.DeptService;
import cn.edu.cqdxxy.boot.service.EmpService;
import com.github.pagehelper.PageInfo;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EmpController {
    @Autowired
    EmpService empService;
    @Autowired
    DeptService deptService;

    /*
    (分页查询)员工
    1.scopeAge为查询范围，前端值为18-25，需要以"-"分割成两个变量。
    2.对接收的变量非空校验然后再trim()去空字符串。
    3.调用分页方法，将结果集存入域中.
     */
    @GetMapping("/empCRUD")//分页查询分页查询分页查询分页查询分页查询分页查询分页查询分页查询分页查询分页查询分页查询分页查询分页查询分页查询分页查询分页查询分页查询
    public String pagingBook(@RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "5") Integer pageSize,
                             Model model,
                             String name, String gender, String age, String job,
                             String scopeAge, String deptId) {
        //emp接收数据有name,gender,age,job,deptId
        Integer ageBegin = null;
        Integer ageEnd = null;
        System.out.println("scopeAge=" + scopeAge);
        //新建一个conditionQueryEmp对象，接收查询带了哪些条件，转给显示页面，用于发条件查询。
        ConditionQueryEmp conditionQueryEmp = new ConditionQueryEmp();
        conditionQueryEmp.setName(name);
        conditionQueryEmp.setGender(gender);
        conditionQueryEmp.setAge(age);
        conditionQueryEmp.setJob(job);
        conditionQueryEmp.setScopeAge(scopeAge);
        conditionQueryEmp.setDeptId(deptId);
        System.out.println("conditionQueryEmp="+conditionQueryEmp);
        //传入查询条件，传到页面，用于点击下一页图标发送查询条件
        model.addAttribute("conditionQueryEmp",conditionQueryEmp);
        if (scopeAge != null && !scopeAge.equals("")) {
            System.out.println("scopeAgeGetinto....");
            String[] split = scopeAge.split("-");
            ageBegin = Integer.parseInt(split[0]);
            ageEnd = Integer.parseInt(split[1]);
        }
        if (name != null && age != null && gender != null) {
            name = name.trim();
            age = age.trim();
            gender = gender.trim();
        }
        PageInfo<Employee> pageInfo = empService.page(page, pageSize, name, gender, age, job, deptId, ageBegin, ageEnd);
        System.out.println(pageInfo);
        //传入分页结果集
        model.addAttribute("pageInfo", pageInfo);
        //传入当前选择了一页多少条数据
        model.addAttribute("pageSize", pageSize);
        //传入所有的部门
        List<Dept> allDept = deptService.findAllDept();
        model.addAttribute("allDept", allDept);
        return "emp/empCRUD";
    }//分页查询分页查询分页查询分页查询分页查询分页查询分页查询分页查询分页查询分页查询分页查询分页查询分页查询分页查询分页查询分页查询分页查询分页查询分页查询分页查询分页查询分页查询分页查询

    /*
    1.(删除员工)删除员工删除员工删除员工删除员工删除员工删除员工删除员工删除员工删除员工删除员工删除员工删除员工删除员工删除员工删除员工删除员工
     */
    @GetMapping("/deleteEmp")
    public String dltEmpById(Integer id) {
        empService.deleteEmpById(id);
        return "redirect:empCRUD";
    }//删除员工删除员工删除员工删除员工删除员工删除员工删除员工删除员工删除员工删除员工删除员工删除员工删除员工删除员工删除员工删除员工删除员工删除员工删除员工删除员工删除员工删除员工删除员工删除员工删除员工删除员工

    /*
    1.(批量删除员工)批量删除员工批量删除员工批量删除员工批量删除员工批量删除员工批量删除员工批量删除员工批量删除员工批量删除员工批量删除员工批量删除员工批量删除员工批量删除员工
     */
    @GetMapping("/deleteEmps")
    public String deleteEmps(@RequestParam("ids") String id) {
        System.out.println("stringId=" + id);
        String[] ids = id.split(",");
        for (String s : ids) {
            System.out.println("id=" + s);
        }
        empService.deleteEmpsById(ids);
        return "redirect:empCRUD";
    }//批量删除员工批量删除员工批量删除员工批量删除员工批量删除员工批量删除员工批量删除员工批量删除员工批量删除员工批量删除员工批量删除员工批量删除员工批量删除员工批量删除员工批量删除员工批量删除员工批量删除员工批量删除员工批量删除员工批量删除员工

    /*
    1.1(获取页面)添加员工的添加员工添加员工添加员工添加员工添加员工添加员工添加员工添加员工添加员工添加员工添加员工添加员工添加员工添加员工添加员工添加员工添加员工添加员工
    */
    @GetMapping("/toAddEmp")
    public String toAddEmp(Model model) {
        //因为页面有下拉框，所以需要查询所有部门。
        List<Dept> allDept = deptService.findAllDept();
        model.addAttribute("allDept", allDept);
        return "emp/addEmp";
    }

    /*
    1.2(添加员工)方法，传入接收的员工类,返回到查询员工页面
     */
    @PostMapping("/addEmp")
    public String addEmp(@RequestParam("age") String age, Employee employee, Model model) {
        //因为页面转发了，所以需要重新查询所有部门，然后给到下拉列表。
        List<Dept> allDept = deptService.findAllDept();
        model.addAttribute("allDept", allDept);

        //1.非空校验
        if (employee.getUsername() == null || "".equals(employee.getUsername().trim())) {
            model.addAttribute("addEmpUsernameNull", "用户名不能为空");
            return "emp/addEmp";
        }
        //判断用户名是否重复,寻找是否有页面输入的这个用户
        Employee empByUsername = empService.findEmpByUsername(employee.getUsername());
        if(empByUsername!=null){
            //用户名重复
            model.addAttribute("addEmpUsernameError", "用户名重复，请重新输入！");
            return "emp/addEmp";
        }
        if (employee.getPassword() == null || "".equals(employee.getPassword().trim())) {
            model.addAttribute("addEmpPasswordNull", "密码不能为空");
            return "emp/addEmp";
        }
        if (employee.getName() == null || "".equals(employee.getName().trim())) {
            model.addAttribute("addEmpNameNull", "姓名不能为空");
            return "emp/addEmp";
        }
        if (age == null || "".equals(age.trim())) {
            model.addAttribute("addEmpAgeNull", "年龄不能为空");
            return "emp/addEmp";
        }
        //校验通过,可以添加
        employee.setAge(Integer.parseInt(age));
        empService.addEmp(employee);
        return "redirect:empCRUD";
    }//添加员工添加员工添加员工添加员工添加员工添加员工添加员工添加员工添加员工添加员工添加员工添加员工添加员工添加员工添加员工添加员工添加员工添加员工添加员工添加员工添加员工添加员工添加员工

    /*
    1.(获取页面)修改员工的。修改员工修改员工修改员工修改员工修改员工修改员工修改员工修改员工修改员工修改员工修改员工修改员工修改员工
    根据传过来的id传入查询方法，存入域中，回显数据。
     */
    @GetMapping("/toUpdateEmp")
    public String toUpdateEmp(Integer idEmp, Model model) {
        Employee employee = empService.findEmpById(idEmp);
        model.addAttribute("employee", employee);
        List<Dept> allDept = deptService.findAllDept();
        model.addAttribute("allDept", allDept);
        return "emp/updateEmp";
    }

    /*
    1.(修改员工)根据页面上传过来的数据修改。
     */
    @PostMapping("/updateEmp")
    public void updateEmp(Employee employee,HttpServletResponse resp) throws IOException {
        System.out.println("updateEmp,employee=" + employee);
        empService.updateEmp(employee);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");//location.href='/sparrow/toAddDept'
        resp.getWriter().write("<script>alert('员工修改成功!');history.back()</script>");

    }//修改员工修改员工修改员工修改员工修改员工修改员工修改员工修改员工修改员工修改员工修改员工修改员工修改员工修改员工修改员工修改员工

    /*
    1.(获取页面)页面中有三个图表。
    (1图)调用 分组单表byGender升序查询(性别人数) 方法分为两组。因为男性为1，女性为2，就用count对应上性别.
    (2图)调用 分组单表byJob升序查询(职位人数) 方法分为五组。因为职位有五种，就用count对应上职位.
    (3图)调用 分组(多)表byDeptId升序查询(部门人数) 方法分为五组。特意为它创建了一个类DeptNum作为容器存入list集合.
     */
    //首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页
    @GetMapping("/toIndex")
    public String toIndex(Model model,HttpSession session) {
        boolean isAdmin = (boolean)session.getAttribute("isAdmin");

        if(isAdmin){
            Admin user = (Admin)session.getAttribute("user");
            System.out.println("初始头像路径"+user.getImagePath());
        }else {
            Employee emp = (Employee)session.getAttribute("user");
            System.out.println("初始头像路径"+emp.getImagePath());
        }

        int count = 1;
        //1.多表查询男女人数
        List<Integer> genderNum = empService.pagingGenderNum();
        Map<String, Integer> genderMap = new HashMap<>();
        for (Integer num : genderNum) {
            switch (count) {
                case 1: {
                    genderMap.put("男性员工", num);
                }
                case 2: {
                    genderMap.put("女性员工", num);
                }
                count++;
            }
        }
        model.addAttribute("genderMap", genderMap);
        //2.多表查询职位人数
        List<Integer> jobNum = empService.pagingJobNum();
        Map<String, Integer> jobMap = new HashMap<>();
        count = 1;
        for (Integer num : jobNum) {
            switch (count) {
                case 1: {
                    jobMap.put("经理", num);
                }
                case 2: {
                    jobMap.put("副经理", num);
                }
                case 3: {
                    jobMap.put("主管", num);
                }
                case 4: {
                    jobMap.put("组长", num);
                }
                case 5: {
                    jobMap.put("职工", num);
                }
                count++;
            }
        }
        model.addAttribute("jobMap", jobMap);
        //3.多表查询部门人数
        List<DeptNum> deptList = empService.pagingDeptNum();
        model.addAttribute("deptList", deptList);
        return "index";
    }
//首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页首页













}