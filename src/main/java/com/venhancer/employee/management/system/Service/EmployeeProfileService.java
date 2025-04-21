package com.venhancer.employee.management.system.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.venhancer.employee.management.system.DTO.EmployeeProfileDTO;
import com.venhancer.employee.management.system.Entity.Employee;
import com.venhancer.employee.management.system.Entity.EmployeeProfile;
import com.venhancer.employee.management.system.Entity.Manager;
import com.venhancer.employee.management.system.Entity.MyUsersDetails;
import com.venhancer.employee.management.system.Exception.ResourceNotFoundException;
import com.venhancer.employee.management.system.Mapper.AddressMapper;
import com.venhancer.employee.management.system.Mapper.EmployeeProfileMapper;
import com.venhancer.employee.management.system.Repository.EmployeeProfileRepository;
import com.venhancer.employee.management.system.Repository.EmployeeRepository;
import com.venhancer.employee.management.system.Repository.ManagerRepository;

@Service
public class EmployeeProfileService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeProfileRepository employeeProfileRepository;

    @Autowired
    ManagerRepository managerRepository;

    public EmployeeProfileDTO updateEmployeeProfile(Long id, EmployeeProfileDTO employeeProfileDTO){
        
        Authentication authenticatedEmployee = SecurityContextHolder.getContext().getAuthentication();
        MyUsersDetails userDetails = (MyUsersDetails) authenticatedEmployee.getPrincipal();
        Long authenticatedEmployeeId = userDetails.getEmployeeId();
        
        if(!authenticatedEmployeeId.equals(id)){
            throw new RuntimeException("You are not authorized to update this profile.");
        }

        Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee is not found according to given ID"));
        EmployeeProfile employeeProfile = employee.getEmployeeProfile();

        EmployeeProfileMapper.INSTANCE.updateEmployeeProfileFromDTO(employeeProfileDTO, employeeProfile);
        AddressMapper.INSTANCE.updateAddressFromDTO(employeeProfileDTO.getAddress(), employeeProfile.getAddress());

        EmployeeProfile savedEmployeeProfile = employeeProfileRepository.save(employeeProfile);
        return EmployeeProfileMapper.INSTANCE.mapToEmployeeProfileDTO(savedEmployeeProfile);
    }

    public EmployeeProfileDTO getEmployeeProfileById(Long id){
        Authentication authenticatedEmployee = SecurityContextHolder.getContext().getAuthentication();
        MyUsersDetails userDetails = (MyUsersDetails) authenticatedEmployee.getPrincipal();
        
        if(userDetails.getRole().name().equals("EMPLOYEE")){
            if(!userDetails.getEmployeeId().equals(id)){
                throw new RuntimeException("You are not authorized to access this profile.");
            }
        }

        Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee is not found according to given ID"));
        EmployeeProfile employeeProfile = employee.getEmployeeProfile();
        return EmployeeProfileMapper.INSTANCE.mapToEmployeeProfileDTO(employeeProfile);
    }

    public EmployeeProfileDTO updateManagerProfile(Long id, EmployeeProfileDTO managerProfileDTO) {
        Authentication authenticatedManager = SecurityContextHolder.getContext().getAuthentication();
        MyUsersDetails userDetails = (MyUsersDetails) authenticatedManager.getPrincipal();
        Long authenticatedManagerId = userDetails.getManagerId();

        if(!authenticatedManagerId.equals(id)){
            throw new RuntimeException("You are not authorized to update this profile.");
        }

        Manager manager = managerRepository.findById(authenticatedManagerId)
                    .orElseThrow(()-> new ResourceNotFoundException("Manager is not found according to given ID"));
        EmployeeProfile managerProfile = manager.getManagerProfile();

        EmployeeProfileMapper.INSTANCE.updateEmployeeProfileFromDTO(managerProfileDTO, managerProfile);
        AddressMapper.INSTANCE.updateAddressFromDTO(managerProfileDTO.getAddress(), managerProfile.getAddress());

        EmployeeProfile savedManagerProfile = employeeProfileRepository.save(managerProfile);
        return EmployeeProfileMapper.INSTANCE.mapToEmployeeProfileDTO(savedManagerProfile);
    }

    public EmployeeProfileDTO getManagerProfileById(Long id) {
        Authentication authenticatedManager = SecurityContextHolder.getContext().getAuthentication();
        MyUsersDetails userDetails = (MyUsersDetails) authenticatedManager.getPrincipal();

        if(userDetails.getRole().name().equals("MANAGER")){
            if(!userDetails.getManagerId().equals(id)){
                throw new RuntimeException("You are not authorized to access this profile.");
            }
        }
        
        Manager manager = managerRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Manager is not found according to given ID"));
        EmployeeProfile managerProfile = manager.getManagerProfile();
        return EmployeeProfileMapper.INSTANCE.mapToEmployeeProfileDTO(managerProfile);
    }
}