package cn.edu.cqdxxy.boot.service;

import cn.edu.cqdxxy.boot.entity.Dept;
import cn.edu.cqdxxy.boot.entity.Employee;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface DeptService {
    List<Dept> findAllDept();

    Dept findDeptById(Integer id);

    List<Employee> findEmpsByDeptId(Integer deptId);


    PageInfo<Employee> deptDetailPage(Integer page, Integer pageSize, Integer id);

    void addDept(String deptName, String imagePath, String imageName);

    void deleteDeptById(Integer id);

    void updateDeptById(Integer id, String deptName,String imagePath, String imageName);
}
