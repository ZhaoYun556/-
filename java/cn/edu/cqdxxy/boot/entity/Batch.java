package cn.edu.cqdxxy.boot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Batch {
    private Integer id;
    private String content;
    private Timestamp createTime;
    private Integer love;
    private String username;
    private String imagePatha;
    private String imagePathe;
    private boolean isAdmin;
    private String name;
    private Integer idAdmin;
    private Integer idEmp;
    public Boolean getIsAdmin() {
        return isAdmin;
    }
}
