package cn.edu.cqdxxy.boot.service.Impl;

import cn.edu.cqdxxy.boot.entity.DeptNum;
import cn.edu.cqdxxy.boot.entity.Employee;
import cn.edu.cqdxxy.boot.mapper.EmployeeMapper;
import cn.edu.cqdxxy.boot.service.EmpService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    EmployeeMapper empMapper;

    @Override
    public void addEmp(Employee employee) {
        empMapper.addEmp(employee);
    }


    @Override
    public void modifyInfo(Integer id,String name, String imagePath,String originalFilename) {
        empMapper.modifyInfo(id,name,imagePath,originalFilename);
    }

    @Override
    public void deleteEmpById(Integer id) {
        empMapper.deleteEmpById(id);
    }
    @Override
    public void deleteEmpsById(String[] ids) {
        empMapper.deleteEmpsById(ids);
    }
    @Override
    public Employee findEmpById(Integer id) {
        return empMapper.findEmpById(id);
    }

    @Override
    public void updateEmp(Employee employee) {
        empMapper.updateEmp(employee);
    }

    @Override
    public PageInfo<Employee> page(Integer page, Integer pageSize, String name, String gender,
                                   String age, String job, String deptId, Integer ageBegin, Integer ageEnd) {
        PageHelper.startPage(page,pageSize);
        List<Employee> list = empMapper.empList(name,gender,age,job,deptId,ageBegin,ageEnd);
        PageInfo<Employee> pageInfo = new PageInfo<>(list,5);
        return pageInfo;
    }

    @Override
    public Employee findEmpByUsername(String username) {
        return empMapper.findEmpByUsername(username);
    }


    @Override//1.多表查询男女人数
    public List<Integer> pagingGenderNum() {
        return empMapper.pagingGenderNum();
    }

    @Override//2.多表查询职位人数
    public List<Integer> pagingJobNum() {
        return empMapper.pagingJobNum();
    }

    @Override//3.多表查询部门人数
    public List<DeptNum> pagingDeptNum() {
        return empMapper.pagingDeptNum();
    }

    @Override//修改密码
    public void modifyPwd(Integer id,String newPassword) {
        empMapper.modifyPwd(id,newPassword);
    }




}
