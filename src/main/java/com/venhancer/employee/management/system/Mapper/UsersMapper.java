package com.venhancer.employee.management.system.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.venhancer.employee.management.system.DTO.UsersDTO;
import com.venhancer.employee.management.system.Entity.Users;

@Mapper
public interface UsersMapper {

    UsersMapper INSTANCE = Mappers.getMapper(UsersMapper.class);

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "manager.id", target = "managerId")
    UsersDTO mapToUsersDTO (Users user);

    @Mapping(source = "employeeId", target = "employee.id")
    @Mapping(source = "managerId", target = "manager.id")
    Users mapToUsers(UsersDTO usersDTO);

}
