package cn.edu.cqdxxy.boot.service;

import cn.edu.cqdxxy.boot.entity.DeptNum;
import cn.edu.cqdxxy.boot.entity.Employee;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmpService {
     void addEmp(Employee employee) ;



    void deleteEmpById(Integer id);

    Employee findEmpById(Integer id);
    void updateEmp(Employee employee);

    PageInfo<Employee> page(Integer page, Integer pageSize, String name, String gender,
                            String age, String job, String deptId, Integer ageBegin, Integer ageEnd);


    Employee findEmpByUsername(String username);

    List<Integer> pagingGenderNum();//3.多表查询性别人数

    List<Integer> pagingJobNum();//3.多表查询职位人数

    List<DeptNum> pagingDeptNum();//3.多表查询部门人数

    void modifyPwd(Integer id,String newPassword);//修改密码

    void deleteEmpsById(String[] ids);

    void modifyInfo(Integer id,String name, String imagePath,String originalFilename);
}
