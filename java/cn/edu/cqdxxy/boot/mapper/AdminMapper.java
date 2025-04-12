package cn.edu.cqdxxy.boot.mapper;

import cn.edu.cqdxxy.boot.entity.Admin;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface AdminMapper {
    List<Admin> findAllAdminByParam(Integer id,String username);
    @Select("select * from admin")
    List<Admin> findAllAdmin();//查询所有admin,不带条件
    void addAdmin(String username, String password, String imageName, String imagePath, Timestamp creatTime, Timestamp updateTime);
@Delete("delete from admin where id_admin=#{id}")
    void deleteAdminById(Integer id);
    void deleteAdminsById(String[] ids);
@Select("select * from admin where id_admin=#{id}")
    Admin findAdminById(Integer id);

    void updateAdmin(Admin admin);
@Select("select * from admin where username=#{username}")
    Admin findAdminByUsername(String username);

    void modifyInfo(Integer id, String imagePath, String imageName);
@Update("update admin set password=#{newPassword} where id_admin=#{id}")
    void modifyPwd(Integer id, String newPassword);
}
