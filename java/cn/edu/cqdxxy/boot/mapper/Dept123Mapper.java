package cn.edu.cqdxxy.boot.mapper;

import cn.edu.cqdxxy.boot.entity.Dept;
import cn.edu.cqdxxy.boot.entity.Employee;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.Timestamp;
import java.util.List;
@Mapper
public interface Dept123Mapper {
    @Select("select * from dept")
    List<Dept> findAllDept();

    @Select("select * from dept where id=#{id}")
    Dept findDeptById(Integer id);

    @Select("select * from test where dept_id =#{deptId}")
    List<Employee> findEmpsByDeptId(Integer deptId);

    void addDept(String deptName, String imagePath, String imageName, Timestamp timestamp);

    @Delete("delete from dept where id=#{id}")
    void deleteDeptById(Integer id);

    void updateDeptById(String deptName, Timestamp updateTime, String imagePath, String imageName, Integer id);







}
