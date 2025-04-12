package cn.edu.cqdxxy.boot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConditionQueryEmp {
    private String name;
    private String gender;
    private String age;
    private String job;
    private String scopeAge;
    private String deptId;
}
