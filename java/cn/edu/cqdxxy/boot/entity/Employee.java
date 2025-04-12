package cn.edu.cqdxxy.boot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private Integer idEmp;
    private String username;
    private String password;
    private String name;
    private Short  gender;
    private String imageName;
    private String imagePath;
    private Integer age;
    private Short job;
    private LocalDate entrydate;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer deptId;
    private boolean isAdmin=false;
    public Boolean getIsAdmin() {
        return isAdmin;
    }
}
