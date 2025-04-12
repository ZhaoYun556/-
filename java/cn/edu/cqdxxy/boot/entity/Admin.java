package cn.edu.cqdxxy.boot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    private Integer idAdmin;
    private String username;
    private String password;
    private String imagePath;
    private String imageName;
    private Timestamp createTime;
    private Timestamp updateTime;
    private boolean isAdmin = true;

    public Boolean getIsAdmin() {
        return isAdmin;
    }
}
