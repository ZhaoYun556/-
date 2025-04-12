package cn.edu.cqdxxy.boot.mapper;

import cn.edu.cqdxxy.boot.entity.DeptNum;
import cn.edu.cqdxxy.boot.entity.Employee;
import org.apache.ibatis.annotations.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmployeeMapper {
    @Delete("delete from test where id_emp = #{id}")
    void deleteEmpById(@Param("id") Integer id);//删除单个emp,sql语句
    void deleteEmpsById(String[] ids);//批量删除emp,sql语句
    void addEmp(Employee employee);
    @Select("select * from test where id_emp=#{id}")
    Employee findEmpById(Integer id);
    void updateEmp(Employee employee);

    List<Employee> empList(
                        @Param("name") String name,@Param("gender") String gender,@Param("age") String age,
                        @Param("job") String job,@Param("deptId") String deptId,@Param("ageBegin") Integer ageBegin,@Param("ageEnd") Integer ageEnd);

@Select("select * from test where username=#{username}")
    Employee findEmpByUsername(String username);
    List<Integer> pagingGenderNum();//1.多表查询男女人数
    List<Integer> pagingJobNum();//2.多表查询职位人数
/*
    @Select("select d.name,count(t.id) from dept d left  join test t on d.id=t.dept_id group by d.id order by d.id asc")
*/
    List<DeptNum> pagingDeptNum();//3.多表查询部门人数



    @Update("update test set password=#{newPassword} where id_emp=#{id}")
    void modifyPwd(Integer id, String newPassword);

    void modifyInfo(Integer id,String name, String imagePath,String originalFilename);
}
