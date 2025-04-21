package com.venhancer.employee.management.system.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.venhancer.employee.management.system.DTO.EmployeeDTO;
import com.venhancer.employee.management.system.Entity.Employee;

@Mapper(uses = EmployeeProfileMapper.class)
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    @Mapping(target = "departmentId", source = "department.id")
    @Mapping(target = "employeeProfileId", source = "employeeProfile.id")
    @Mapping(target = "managerId", source = "manager.id")
    EmployeeDTO mapToEmployeeDTO(Employee employee);

    @Mapping(target = "department.id", source = "departmentId")
    @Mapping(target = "employeeProfile.id", source = "employeeProfileId")
    @Mapping(target = "manager.id", source = "managerId")
    @Mapping(target = "users", ignore = true)
    Employee mapToEmployee(EmployeeDTO employeeDTO);
}
