package com.venhancer.employee.management.system.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.venhancer.employee.management.system.DTO.EmployeeProfileDTO;
import com.venhancer.employee.management.system.Entity.Employee;
import com.venhancer.employee.management.system.Entity.EmployeeProfile;
import com.venhancer.employee.management.system.Exception.ResourceNotFoundException;
import com.venhancer.employee.management.system.Mapper.EmployeeProfileMapper;
import com.venhancer.employee.management.system.Repository.EmployeeProfileRepository;
import com.venhancer.employee.management.system.Repository.EmployeeRepository;

@Service
public class EmployeeProfileService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeProfileRepository employeeProfileRepository;

    @PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
    public EmployeeProfileDTO updateEmployeeProfileDTO(Long id, EmployeeProfileDTO employeeProfileDTO){
        Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee is not found according to given ID"));
        EmployeeProfile employeeProfile = employeeProfileRepository.findById(employee.getEmployeeProfile().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee's profile is not found according to given ID"));
        if(employeeProfileDTO.getName() != null){
            employeeProfile.setName(employeeProfileDTO.getName());
        }
        if(employeeProfileDTO.getSurname() != null){
            employeeProfile.setSurname(employeeProfileDTO.getSurname());
        }
        if(employeeProfileDTO.getEmail() != null){
            employeeProfile.setEmail(employeeProfileDTO.getEmail());
        }
        if(employeeProfileDTO.getPhoneNumber() != null){
            employeeProfile.setPhoneNumber(employeeProfileDTO.getPhoneNumber());
        }
        if(employeeProfileDTO.getAddress() != null){
            employeeProfile.setAddress(employeeProfileDTO.getAddress());
        }

        EmployeeProfile savedEmployeeProfile = employeeProfileRepository.save(employeeProfile);
        return EmployeeProfileMapper.INSTANCE.mapToEmployeeProfileDTO(savedEmployeeProfile);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MANAGER') or hasAuthority('ROLE_EMPLOYEE')")
    public EmployeeProfileDTO getEmployeeProfileById(Long id){
        EmployeeProfile employeeProfile = employeeProfileRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee's profile is not found according to given ID"));
        return EmployeeProfileMapper.INSTANCE.mapToEmployeeProfileDTO(employeeProfile);
    }
}
