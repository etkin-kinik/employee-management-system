package com.venhancer.employee.management.system.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.venhancer.employee.management.system.DTO.EmployeeDTO;
import com.venhancer.employee.management.system.DTO.ManagerDTO;
import com.venhancer.employee.management.system.DTO.UsersDTO;
import com.venhancer.employee.management.system.Entity.Employee;
import com.venhancer.employee.management.system.Entity.Manager;
import com.venhancer.employee.management.system.Entity.Users;
import com.venhancer.employee.management.system.Mapper.UsersMapper;
import com.venhancer.employee.management.system.Repository.EmployeeRepository;
import com.venhancer.employee.management.system.Repository.ManagerRepository;
import com.venhancer.employee.management.system.Repository.UsersRepository;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    EmployeeRepository employeeRepository;
    
    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    EmployeeService employeeService;
    
    @Autowired
    ManagerService managerService;

    @Autowired
    JWTService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UsersDTO registerUser(UsersDTO usersDto){

        Users users = UsersMapper.INSTANCE.mapToUsers(usersDto);
        users.setPassword(encoder.encode(users.getPassword()));

        if(usersRepository.findByUsername(users.getUsername()) != null){
            throw new RuntimeException("Username is already exist.");
        }

        if(usersDto.getEmployeeId() != null && usersDto.getRole().name().equals("EMPLOYEE")){
            Employee employee = employeeRepository.findById(usersDto.getEmployeeId())
                        .orElseThrow(() -> new RuntimeException("Employee is not found according to given id"));
            users.setName(employee.getName());
            users.setSurname(employee.getSurname());
            users.setDepartmentId(employee.getDepartment().getId());
            users.setEmployee(employee);
            users.setManager(null);
        } else if(usersDto.getEmployeeId() == null && usersDto.getRole().name().equals("EMPLOYEE")){
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setName(usersDto.getName());
            employeeDTO.setSurname(usersDto.getSurname());
            employeeDTO.setDepartmentId(usersDto.getDepartmentId());

            EmployeeDTO newEmployeeDTO = employeeService.createEmployee(employeeDTO);
            Employee newEmployee = employeeRepository.findById(newEmployeeDTO.getId())
                        .orElseThrow(() -> new RuntimeException("Failed to create a new employee"));
            users.setManager(null);
            users.setEmployee(newEmployee);
        }
        if(usersDto.getManagerId() != null && usersDto.getRole().name().equals("MANAGER")){
            Manager manager = managerRepository.findById(usersDto.getManagerId())
                        .orElseThrow(() -> new RuntimeException("Manager is not found according to given id"));
            users.setName(manager.getName());
            users.setSurname(manager.getSurname());
            if(manager.getDepartment() != null){
                users.setDepartmentId(manager.getDepartment().getId());
            } else {
                users.setDepartmentId(null);
            }
            users.setManager(manager);
            users.setEmployee(null);
        } else if(usersDto.getManagerId() == null && usersDto.getRole().name().equals("MANAGER")){
            ManagerDTO managerDTO = new ManagerDTO();
            managerDTO.setName(usersDto.getName());
            managerDTO.setSurname(usersDto.getSurname());
            if(usersDto.getDepartmentId() != null){
                managerDTO.setDepartmentId(usersDto.getDepartmentId());
            } else {
                managerDTO.setDepartmentId(null);
            }

            ManagerDTO newManagerDTO = managerService.createManager(managerDTO);
            Manager newManager = managerRepository.findById(newManagerDTO.getId())
                        .orElseThrow(() -> new RuntimeException("Failed to create a new manager"));
            users.setEmployee(null);
            users.setManager(newManager);
        }

        if(usersDto.getRole().name().equals("ADMIN")){
            users.setName(usersDto.getName());
            users.setSurname(usersDto.getSurname());
            users.setEmployee(null);
            users.setManager(null);
        }

        Users savedUsers = usersRepository.save(users);
        return UsersMapper.INSTANCE.mapToUsersDTO(savedUsers);
    }

    public String verify(UsersDTO usersDTO) {
        Users users = UsersMapper.INSTANCE.mapToUsers(usersDTO);
        Authentication authentication = 
                        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(users.getUsername(), users.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(users);
        } else {
            return "Fail";
        }
    }

    public void deleteUsers(Long id) {
        Users user = usersRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User is not found according to the given Id"));
        if(user.getRole().name().equals("EMPLOYEE")){
            user.getEmployee().setUsers(null);
            user.setEmployee(null);
        } else if(user.getRole().name().equals("MANAGER")){
            user.getManager().setUsers(null);
            user.setManager(null);
        }
        usersRepository.delete(user);
    }

}

