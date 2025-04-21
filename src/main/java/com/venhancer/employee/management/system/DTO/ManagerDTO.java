package com.venhancer.employee.management.system.DTO;

import java.util.List;

import com.venhancer.employee.management.system.Validation.OnCreate;
import com.venhancer.employee.management.system.Validation.OnUpdate;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerDTO {
    private Long id;

    @NotNull(message = "Name cannot be null", groups = OnCreate.class)
    private String name;

    @NotNull(message = "Surname cannot be null", groups = OnCreate.class)
    private String surname;
    
    @NotNull(message = "Department id cannot be null", groups = OnUpdate.class)
    private Long departmentId;

    private List<EmployeeDTO> employees;
    private Long managerProfileId;
    private EmployeeProfileDTO managerProfile;
}
