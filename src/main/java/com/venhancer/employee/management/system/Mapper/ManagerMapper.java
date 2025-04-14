package com.venhancer.employee.management.system.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.venhancer.employee.management.system.DTO.ManagerDTO;
import com.venhancer.employee.management.system.Entity.Manager;

@Mapper(uses = EmployeeMapper.class)
public interface ManagerMapper {

    ManagerMapper INSTANCE = Mappers.getMapper(ManagerMapper.class);

    @Mapping(target = "departmentId", source = "department.id")
    @Mapping(target = "employees", source = "employee")
    ManagerDTO mapToManagerDTO(Manager manager);

    @Mapping(target = "department.id", source = "departmentId")
    @Mapping(target = "employee", source = "employees")
    @Mapping(target = "users", ignore = true)
    Manager mapToManager(ManagerDTO managerDTO);
}