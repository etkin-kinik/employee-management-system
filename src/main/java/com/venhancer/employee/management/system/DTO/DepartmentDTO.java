package com.venhancer.employee.management.system.DTO;

import java.util.List;

import com.venhancer.employee.management.system.Validation.OnCreate;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentDTO {
    private Long id;

    @NotNull(message = "Name cannot be null", groups = OnCreate.class)
    private String name;

    private Long managerId;
    private List<EmployeeDTO> employee;
}
