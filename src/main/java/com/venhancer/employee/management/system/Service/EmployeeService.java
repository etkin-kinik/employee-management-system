package com.venhancer.employee.management.system.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venhancer.employee.management.system.DTO.EmployeeDTO;
import com.venhancer.employee.management.system.Entity.Department;
import com.venhancer.employee.management.system.Entity.Employee;
import com.venhancer.employee.management.system.Entity.EmployeeProfile;
import com.venhancer.employee.management.system.Entity.Manager;
import com.venhancer.employee.management.system.Entity.Users;
import com.venhancer.employee.management.system.Exception.ResourceNotFoundException;
import com.venhancer.employee.management.system.Mapper.EmployeeMapper;
import com.venhancer.employee.management.system.Repository.DepartmentRepository;
import com.venhancer.employee.management.system.Repository.EmployeeProfileRepository;
import com.venhancer.employee.management.system.Repository.EmployeeRepository;
import com.venhancer.employee.management.system.Repository.ManagerRepository;
import com.venhancer.employee.management.system.Repository.UsersRepository;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    EmployeeProfileRepository employeeProfileRepository;

    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    UsersRepository usersRepository;

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO){
        Employee employee = EmployeeMapper.INSTANCE.mapToEmployee(employeeDTO);

        Department department = departmentRepository.findById(employeeDTO.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department is not found according to given ID"));
        employee.setDepartment(department);

        if(department.getManager() != null){
            Manager manager = managerRepository.findById(department.getManager().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Manager is not found according to given ID"));

            employee.setManager(department.getManager());
            manager.setEmployee(new ArrayList<>(department.getEmployee()));
            managerRepository.save(manager);
        } else {
            employee.setManager(null);
        }

        Employee savedEmployee = employeeRepository.save(employee);

        if (savedEmployee.getEmployeeProfile() != null) {
            EmployeeProfile employeeProfile = employee.getEmployeeProfile();
            employeeProfile.setName(savedEmployee.getName());
            employeeProfile.setSurname(savedEmployee.getSurname());
            employeeProfile.setEmployeeId(savedEmployee.getId());
            employeeProfile = employeeProfileRepository.save(employeeProfile);
            employee.setEmployeeProfile(employeeProfile);
        }

        return EmployeeMapper.INSTANCE.mapToEmployeeDTO(savedEmployee);
    }

    public EmployeeDTO getEmployeeById(Long id){
        Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee is not found according to given ID"));
        return EmployeeMapper.INSTANCE.mapToEmployeeDTO(employee);
    }

    public List<EmployeeDTO> getAllEmployees(){
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map((item) -> EmployeeMapper.INSTANCE.mapToEmployeeDTO(item)).collect(Collectors.toList());
    }

    public List<EmployeeDTO> getEmployeesByDepartmentName(String departmentName){
        List<Employee> employees = employeeRepository.findByDepartmentName(departmentName);
        return employees.stream().map((item) -> EmployeeMapper.INSTANCE.mapToEmployeeDTO(item)).collect(Collectors.toList());
    }

    public EmployeeDTO updateEmployeesDepartment(Long id, EmployeeDTO employeeDTO){
        Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee is not found according to given ID"));
        Department department = departmentRepository.findById(employeeDTO.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department is not found according to given ID"));        

        employee.setDepartment(department);
        employee.setManager(department.getManager());

        employeeRepository.save(employee);
        return EmployeeMapper.INSTANCE.mapToEmployeeDTO(employee);
    }

    public void deleteEmployee(Long id){
        Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee is not found according to given ID"));

        EmployeeProfile employeeProfile = employee.getEmployeeProfile();

        if(employeeProfile != null){
            employee.setEmployeeProfile(null);
            employeeProfile.setEmployee(null);

            employeeRepository.save(employee);
            employeeProfileRepository.save(employeeProfile);
            
            employeeProfileRepository.delete(employeeProfile);
        }

        Users user = employee.getUsers();
        if(user != null){
            employee.setUsers(null);
            usersRepository.delete(user);
        }
        
        employeeRepository.delete(employee);
    }

}
