package com.venhancer.employee.management.system.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.venhancer.employee.management.system.DTO.AddressDTO;
import com.venhancer.employee.management.system.Entity.Address;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    AddressDTO mapToAddressDTO(Address address);

    @Mapping(target = "employeeProfile", ignore = true)
    Address mapToAddress(AddressDTO addressDTO);

    @Mapping(target = "employeeProfile", ignore = true)
    void updateAddressFromDTO(AddressDTO addressDTO, @MappingTarget Address address);
}