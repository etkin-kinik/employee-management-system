package com.venhancer.employee.management.system.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.venhancer.employee.management.system.DTO.EmployeeProfileDTO;
import com.venhancer.employee.management.system.Entity.EmployeeProfile;

@Mapper
public interface EmployeeProfileMapper {

    EmployeeProfileMapper INSTANCE = Mappers.getMapper(EmployeeProfileMapper.class);

    @Mapping(target = "employeeId", source = "employee.id")
    EmployeeProfileDTO mapToEmployeeProfileDTO(EmployeeProfile employeeProfile);

    @Mapping(target = "employee.id", source = "employeeId")
    EmployeeProfile mapToEmployeeProfile(EmployeeProfileDTO employeeProfileDTO);
}
