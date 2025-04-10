package com.venhancer.employee.management.system.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.venhancer.employee.management.system.DTO.DepartmentDTO;
import com.venhancer.employee.management.system.Entity.Department;
import com.venhancer.employee.management.system.Entity.Employee;
import com.venhancer.employee.management.system.Entity.Manager;
import com.venhancer.employee.management.system.Exception.ResourceNotFoundException;
import com.venhancer.employee.management.system.Mapper.DepartmentMapper;
import com.venhancer.employee.management.system.Repository.DepartmentRepository;
import com.venhancer.employee.management.system.Repository.EmployeeRepository;
import com.venhancer.employee.management.system.Repository.ManagerRepository;

@Service
public class DepartmentService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;


    @Autowired
    ManagerRepository managerRepository;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO){
        Department department = DepartmentMapper.INSTANCE.mapToDepartment(departmentDTO);

        if(departmentDTO.getManagerId() != null){
            Manager manager = managerRepository.findById(departmentDTO.getManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Manager is not found according to given ID"));
            department.setManager(manager);
        } else {
            department.setManager(null);
        }

        Department savedDepartment = departmentRepository.save(department);
        return DepartmentMapper.INSTANCE.mapToDepartmentDTO(savedDepartment);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MANAGER')")
    public List<DepartmentDTO> getAllDepartments(){
        List<Department> allDepartments = departmentRepository.findAll();
        return allDepartments.stream().map((item) -> DepartmentMapper.INSTANCE.mapToDepartmentDTO(item)).collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MANAGER')")
    public DepartmentDTO getDepartmentById(Long id){
        Department department = departmentRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Department is not found according to given ID"));
        return DepartmentMapper.INSTANCE.mapToDepartmentDTO(department);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MANAGER')")
    public DepartmentDTO getDepartmentByDepartmentName(String departmentName){
        Department department = departmentRepository.findDepartmentByDepartmentName(departmentName);
        return DepartmentMapper.INSTANCE.mapToDepartmentDTO(department);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteDepartment(Long id){
        Department department = departmentRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Department is not found according to given ID"));
        
        if(department.getManager() != null && department.getEmployee() != null){
            Manager manager = managerRepository.findById(department.getManager().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Manager is not found according to given ID"));
            for(Employee employee: department.getEmployee()){
                employee.setManager(null);
                employee.setDepartment(null);

                employeeRepository.save(employee);
            }
            department.setEmployee(null);
            department.setManager(null);

            manager.setDepartment(null);
            managerRepository.save(manager);
        } else if (department.getManager() == null && department.getEmployee() != null){
            for(Employee employee : department.getEmployee()){
                employee.setDepartment(null);

                employeeRepository.save(employee);
            }
            department.setEmployee(null);
        }
        departmentRepository.delete(department);
    }

}
