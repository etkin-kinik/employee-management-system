package com.venhancer.employee.management.system.DTO;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerDTO {
    private Long id;
    private String name;
    private String surname;
    private Long departmentId;
    private List<EmployeeDTO> employees;
}
