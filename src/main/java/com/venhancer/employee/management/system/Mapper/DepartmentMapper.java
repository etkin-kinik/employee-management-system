package com.venhancer.employee.management.system.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.venhancer.employee.management.system.DTO.DepartmentDTO;
import com.venhancer.employee.management.system.Entity.Department;

@Mapper(uses = EmployeeMapper.class)
public interface DepartmentMapper {

    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);

    @Mapping(target = "managerId", source = "manager.id")
    DepartmentDTO mapToDepartmentDTO(Department department);

    @Mapping(target = "manager.id", source = "managerId")
    Department mapToDepartment(DepartmentDTO departmentDTO);
}
