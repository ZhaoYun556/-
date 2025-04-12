package cn.edu.cqdxxy.boot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Dept {
    private Integer id;
    private String name;
    private String imageName;
    private String imagePath;
    private Timestamp createTime;
    private Timestamp updateTime;
}
