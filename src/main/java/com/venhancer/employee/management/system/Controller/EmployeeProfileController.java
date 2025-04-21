package com.venhancer.employee.management.system.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.venhancer.employee.management.system.DTO.EmployeeProfileDTO;
import com.venhancer.employee.management.system.Service.EmployeeProfileService;

@RestController
@RequestMapping("/api/ems/employee/profile")
public class EmployeeProfileController {

    @Autowired
    EmployeeProfileService employeeProfileService;

    @PatchMapping("/employee-update/{id}")
    public ResponseEntity<EmployeeProfileDTO> updatedEmployeeProfile(@PathVariable Long id, @RequestBody EmployeeProfileDTO employeeProfileDTO){
        EmployeeProfileDTO employeeProfile = employeeProfileService.updateEmployeeProfile(id, employeeProfileDTO);
        return ResponseEntity.ok(employeeProfile);
    }

    @PatchMapping("/manager-update/{id}")
    public ResponseEntity<EmployeeProfileDTO> updatedManagerProfile(@PathVariable Long id, @RequestBody EmployeeProfileDTO managerProfileDTO){
        EmployeeProfileDTO managerProfile = employeeProfileService.updateManagerProfile(id, managerProfileDTO);
        return ResponseEntity.ok(managerProfile);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeProfileDTO> getEmployeeProfileById(@PathVariable Long id){
        EmployeeProfileDTO employeeProfile = employeeProfileService.getEmployeeProfileById(id);
        return ResponseEntity.ok(employeeProfile);
    }

    @GetMapping("/manager/{id}")
    public ResponseEntity<EmployeeProfileDTO> getManagerProfileById(@PathVariable Long id){
        EmployeeProfileDTO managerProfile = employeeProfileService.getManagerProfileById(id);
        return ResponseEntity.ok(managerProfile);
    }
}
