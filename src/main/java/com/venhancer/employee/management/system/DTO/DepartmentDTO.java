package com.venhancer.employee.management.system.DTO;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentDTO {
    private Long id;
    private String name;
    private Long managerId;
    private List<EmployeeDTO> employee;
}
