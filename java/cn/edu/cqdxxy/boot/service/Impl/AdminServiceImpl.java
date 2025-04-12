package cn.edu.cqdxxy.boot.service.Impl;

import cn.edu.cqdxxy.boot.entity.Admin;
import cn.edu.cqdxxy.boot.entity.Employee;
import cn.edu.cqdxxy.boot.mapper.AdminMapper;
import cn.edu.cqdxxy.boot.service.AdminService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Override
    public PageInfo<Admin> pageAdmins(Integer page, Integer pageSize,Integer id, String username) {
        PageHelper.startPage(page,pageSize);
        List<Admin> allAdmin = adminMapper.findAllAdminByParam(id,username);
        PageInfo<Admin> pageInfo = new PageInfo<>(allAdmin,5);
        return  pageInfo;
    }

    @Override//查询所有admin,不带条件
    public List<Admin> findAllAdmin() {
        return adminMapper.findAllAdmin();
    }

    @Override
    public void addAdmin(String username, String password, String imageName, String imagePath) {
        // 获取当前时间
        Date currentTime = new Date();
        // 将当前时间转换为Timestamp类型
        Timestamp timestamp = new Timestamp(currentTime.getTime());
        adminMapper.addAdmin(username,password,imageName,imagePath,timestamp,timestamp);
    }

    @Override
    public void deleteAdminById(Integer id) {
        adminMapper.deleteAdminById(id);
    }

    @Override
    public void deleteAdminsById(String[] split) {
        adminMapper.deleteAdminsById(split);
    }
    @Override
    public Admin findAdminById(Integer id) {
        return adminMapper.findAdminById(id);
    }

    @Override
    public void updateAdmin(Admin admin) {
        adminMapper.updateAdmin(admin);
    }

    @Override
    public Admin findAdminByUsername(String username) {
        return adminMapper.findAdminByUsername(username);
    }

    @Override
    public void modifyInfo(Integer id, String imagePath, String originalFilename) {
        adminMapper.modifyInfo(id,imagePath,originalFilename);
    }

    @Override
    public void modifyPwd(Integer id, String newPassword) {
        adminMapper.modifyPwd(id,newPassword);
    }
}
