package com.venhancer.employee.management.system.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.venhancer.employee.management.system.DTO.EmployeeProfileDTO;
import com.venhancer.employee.management.system.Entity.Employee;
import com.venhancer.employee.management.system.Entity.EmployeeProfile;
import com.venhancer.employee.management.system.Entity.MyUsersDetails;
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

    public EmployeeProfileDTO updateEmployeeProfileDTO(Long id, EmployeeProfileDTO employeeProfileDTO){
        
        Authentication authenticatedEmployee = SecurityContextHolder.getContext().getAuthentication();
        MyUsersDetails userDetails = (MyUsersDetails) authenticatedEmployee.getPrincipal();
        Long authenticatedEmployeeId = userDetails.getEmployeeId();
        
        if(!authenticatedEmployeeId.equals(id)){
            throw new RuntimeException("You are not authorized to update this profile.");
        }

        Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee is not found according to given ID"));
        EmployeeProfile employeeProfile = employee.getEmployeeProfile();
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

    public EmployeeProfileDTO getEmployeeProfileById(Long id){
        Authentication authenticatedEmployee = SecurityContextHolder.getContext().getAuthentication();
        MyUsersDetails userDetails = (MyUsersDetails) authenticatedEmployee.getPrincipal();
        
        if(userDetails.getRole().name().equals("EMPLOYEE")){
            if(!userDetails.getEmployeeId().equals(id)){
                throw new RuntimeException("You are not authorized to see this profile.");
            }
        }

        Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee is not found according to given ID"));
        EmployeeProfile employeeProfile = employee.getEmployeeProfile();
        return EmployeeProfileMapper.INSTANCE.mapToEmployeeProfileDTO(employeeProfile);
    }
}
