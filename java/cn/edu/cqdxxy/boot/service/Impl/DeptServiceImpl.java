package cn.edu.cqdxxy.boot.service.Impl;

import cn.edu.cqdxxy.boot.entity.Dept;
import cn.edu.cqdxxy.boot.entity.Employee;
import cn.edu.cqdxxy.boot.mapper.Dept123Mapper;
import cn.edu.cqdxxy.boot.service.DeptService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    Dept123Mapper deptMapper;
    @Override
    public List<Dept> findAllDept() {
        return deptMapper.findAllDept();
    }

    @Override
    public Dept findDeptById(Integer id) {
        return deptMapper.findDeptById(id);
    }

    @Override
    public List<Employee> findEmpsByDeptId(Integer deptId) {
        return deptMapper.findEmpsByDeptId(deptId);
    }

    @Override
    public PageInfo<Employee> deptDetailPage(Integer page,Integer pageSize,Integer id) {
        PageHelper.startPage(page,pageSize);
        List<Employee> empsByDeptIds = deptMapper.findEmpsByDeptId(id);
        PageInfo<Employee> pageInfo = new PageInfo<>(empsByDeptIds,5);
        return pageInfo;
    }

    @Override
    public void addDept(String deptName, String imagePath, String imageName) {
        // 获取当前时间
        Date currentTime = new Date();
        // 将当前时间转换为Timestamp类型
        Timestamp timestamp = new Timestamp(currentTime.getTime());
       deptMapper.addDept(deptName,imagePath,imageName,timestamp);
    }

    @Override
    public void deleteDeptById(Integer id) {
        deptMapper.deleteDeptById(id);
    }

    @Override
    public void updateDeptById(Integer id, String deptName,String imagePath, String imageName) {
        // 获取当前时间
        Date currentTime = new Date();
        // 将当前时间转换为Timestamp类型
        Timestamp timestamp = new Timestamp(currentTime.getTime());
        deptMapper.updateDeptById(deptName,timestamp,imagePath,imageName,id);
    }
}
