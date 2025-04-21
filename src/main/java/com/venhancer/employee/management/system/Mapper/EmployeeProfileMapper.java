package com.venhancer.employee.management.system.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.venhancer.employee.management.system.DTO.EmployeeProfileDTO;
import com.venhancer.employee.management.system.Entity.EmployeeProfile;

@Mapper(uses = AddressMapper.class, 
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface EmployeeProfileMapper {

    EmployeeProfileMapper INSTANCE = Mappers.getMapper(EmployeeProfileMapper.class);

    @Mapping(target = "addressId", source = "address.id")
    EmployeeProfileDTO mapToEmployeeProfileDTO(EmployeeProfile employeeProfile);

    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "address.id", source = "addressId")
    EmployeeProfile mapToEmployeeProfile(EmployeeProfileDTO employeeProfileDTO);

    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "manager", ignore = true)
    void updateEmployeeProfileFromDTO(EmployeeProfileDTO employeeProfileDTO, @MappingTarget EmployeeProfile employeeProfile);
}
