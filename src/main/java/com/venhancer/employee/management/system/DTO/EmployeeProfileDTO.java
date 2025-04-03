package com.venhancer.employee.management.system.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeProfileDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String address;
    private Long employeeId;
}
