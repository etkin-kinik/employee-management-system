package com.venhancer.employee.management.system.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venhancer.employee.management.system.DTO.ManagerDTO;
import com.venhancer.employee.management.system.Entity.Address;
import com.venhancer.employee.management.system.Entity.Department;
import com.venhancer.employee.management.system.Entity.Employee;
import com.venhancer.employee.management.system.Entity.EmployeeProfile;
import com.venhancer.employee.management.system.Entity.Manager;
import com.venhancer.employee.management.system.Entity.Users;
import com.venhancer.employee.management.system.Exception.ResourceNotFoundException;
import com.venhancer.employee.management.system.Mapper.ManagerMapper;
import com.venhancer.employee.management.system.Repository.AddressRepository;
import com.venhancer.employee.management.system.Repository.DepartmentRepository;
import com.venhancer.employee.management.system.Repository.EmployeeProfileRepository;
import com.venhancer.employee.management.system.Repository.EmployeeRepository;
import com.venhancer.employee.management.system.Repository.ManagerRepository;
import com.venhancer.employee.management.system.Repository.UsersRepository;

@Service
public class ManagerService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeProfileRepository employeeProfileRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    ManagerRepository managerRepository;
    
    @Autowired
    UsersRepository usersRepository;

    public ManagerDTO createManager(ManagerDTO managerDTO) {
        Manager manager = ManagerMapper.INSTANCE.mapToManager(managerDTO);

        if(managerDTO.getDepartmentId() != null){
            Department department = departmentRepository.findById(managerDTO.getDepartmentId())
                        .orElseThrow(() -> new ResourceNotFoundException("Department is not found according to given ID"));
            manager.setDepartment(department);

            if(!department.getEmployee().isEmpty()){
                for(Employee employee : department.getEmployee()){
                    employee.setManager(manager);
                }
                manager.setEmployee(new ArrayList<>(department.getEmployee()));
            } else {
                manager.setEmployee(new ArrayList<>());
            }

            department.setManager(manager);
        } else {
            manager.setDepartment(null);
        }

        Manager savedManager = managerRepository.save(manager);

        if(savedManager.getManagerProfile() != null){
            EmployeeProfile managerProfile = manager.getManagerProfile();
            managerProfile.setName(savedManager.getName());
            managerProfile.setSurname(savedManager.getSurname());
            if(manager.getManagerProfile().getAddress() == null){
                manager.getManagerProfile().setAddress(new Address());
            }
            managerProfile = employeeProfileRepository.save(managerProfile);
            manager.setManagerProfile(managerProfile);

            savedManager = managerRepository.save(savedManager);
        }

        return ManagerMapper.INSTANCE.mapToManagerDTO(savedManager);
    }

    public ManagerDTO getManagerById(Long id){
        Manager manager = managerRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Manager is not found according to given ID"));
        return ManagerMapper.INSTANCE.mapToManagerDTO(manager);
    }

    public List<ManagerDTO> getAllManagers(){
        List<Manager> allManagers = managerRepository.findAll();
        return allManagers.stream().map((item) -> ManagerMapper.INSTANCE.mapToManagerDTO(item)).collect(Collectors.toList());
    }

    public ManagerDTO getManagerByDepartmentName(String departmentName){
        Manager manager = managerRepository.findByDepartmentName(departmentName);
        return ManagerMapper.INSTANCE.mapToManagerDTO(manager);
    }
    
    public ManagerDTO updateManagerDepartment(Long id, ManagerDTO managerDTO){
        Manager manager = managerRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Manager is not found according to given ID"));

        Department department = manager.getDepartment();

        if(department != null){
            department = departmentRepository.findById(manager.getDepartment().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department is not found according to given ID"));
        }

        Department nextDepartment = departmentRepository.findById(managerDTO.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Next department is not found according to given ID"));

        if(managerDTO.getDepartmentId() != null && department != null && nextDepartment.getManager() == null){

            department.setManager(null);
            
            for(Employee employee : department.getEmployee()){
                employee.setManager(null);
            }
            manager.setDepartment(nextDepartment);
            nextDepartment.setManager(manager);

            if(nextDepartment.getEmployee() != null) {
                for(Employee employee : nextDepartment.getEmployee()){
                    employee.setManager(manager);
                }
            }
            departmentRepository.save(department);
        }

        else if (managerDTO.getDepartmentId() != null && department == null && nextDepartment.getManager() == null){
            nextDepartment.setManager(manager);
            manager.setDepartment(nextDepartment);
            if(nextDepartment.getEmployee() != null){
                for(Employee employee : nextDepartment.getEmployee()){
                    employee.setManager(manager);
                    employeeRepository.save(employee);
                }
            }
            departmentRepository.save(nextDepartment);
        }
        else if (nextDepartment.getManager() != null){
            throw new IllegalStateException("The next department already has a manager.");
        }
        
        managerRepository.save(manager);
        return ManagerMapper.INSTANCE.mapToManagerDTO(manager);
    }

    public void deleteManager(Long id){
        Manager manager = managerRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Manager is not found according to given ID"));

        Department department = manager.getDepartment();
        if (department != null) {
            department.setManager(null);
            manager.setDepartment(null);
        }

        if(manager.getEmployee() != null){
            for (Employee employee : manager.getEmployee()) {
                employee.setManager(null);
            }
            manager.getEmployee().clear();
        }

        Users user = manager.getUsers();
        if(user != null){
            manager.setUsers(null);
            usersRepository.delete(user);
        }

        managerRepository.delete(manager);
    }
}
