package com.venhancer.employee.management.system.DTO;

import com.venhancer.employee.management.system.Enumeration.Roles;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersDTO {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private Long departmentId;
    private Roles role;
    private Long employeeId;
    private Long managerId;

}
