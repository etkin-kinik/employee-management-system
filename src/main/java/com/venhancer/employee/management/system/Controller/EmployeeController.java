package com.venhancer.employee.management.system.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.venhancer.employee.management.system.DTO.EmployeeDTO;
import com.venhancer.employee.management.system.Service.EmployeeService;
import com.venhancer.employee.management.system.Validation.OnCreate;
import com.venhancer.employee.management.system.Validation.OnUpdate;


@RestController
@RequestMapping("/api/ems/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/create")
    public ResponseEntity<EmployeeDTO> createEmployee(@Validated(OnCreate.class) @RequestBody EmployeeDTO employeeDTO){
        EmployeeDTO createdEmployee = employeeService.createEmployee(employeeDTO);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id){
        EmployeeDTO employeeDTO = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employeeDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(){
        List<EmployeeDTO> allEmployees = employeeService.getAllEmployees();
        return ResponseEntity.ok(allEmployees);
    }

    @GetMapping("/department/{departmentName}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByDepartmentName(@PathVariable String departmentName){
        List<EmployeeDTO> allEmployees = employeeService.getEmployeesByDepartmentName(departmentName);
        return ResponseEntity.ok(allEmployees);
    }

    @PatchMapping("/department-update/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployeesDepartment(@PathVariable Long id, @Validated(OnUpdate.class) @RequestBody EmployeeDTO employeeDTO){
        EmployeeDTO updatedEmployee = employeeService.updateEmployeesDepartment(id, employeeDTO);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee successfully deleted!");
    }
}
