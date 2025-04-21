package com.venhancer.employee.management.system.DTO;

import com.venhancer.employee.management.system.Validation.OnCreate;
import com.venhancer.employee.management.system.Validation.OnUpdate;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDTO {
    private Long id;

    @NotNull(message = "Name cannot be null", groups = OnCreate.class)
    private String name;
    
    @NotNull(message = "Surname cannot be null", groups = OnCreate.class)
    private String surname;

    @NotNull(message = "Department id cannot be null", groups = {OnCreate.class, OnUpdate.class})
    private Long departmentId;

    private Long managerId;
    private Long employeeProfileId;
    private EmployeeProfileDTO employeeProfile;
}