package cn.edu.cqdxxy.boot.service;

import cn.edu.cqdxxy.boot.entity.Admin;
import com.github.pagehelper.PageInfo;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

import java.util.List;

public interface AdminService {
    PageInfo<Admin> pageAdmins(Integer page, Integer pageSize,Integer id, String username);

    List<Admin> findAllAdmin();
    Admin findAdminById(Integer id);

    void addAdmin(String username, String password, String imageName, String imagePath);

    void deleteAdminById(Integer id);

    void deleteAdminsById(String[] split);


    void updateAdmin(Admin admin);

    Admin findAdminByUsername(String username);

    void modifyInfo(Integer id, String imagePath, String originalFilename);

    void modifyPwd(Integer id, String newPassword);
}
