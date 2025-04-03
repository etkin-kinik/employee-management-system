package com.venhancer.employee.management.system.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDTO {
    private Long id;
    private String name;
    private String surname;
    private Long departmentId;
    private Long employeeProfileId;
    private Long managerId;
}
