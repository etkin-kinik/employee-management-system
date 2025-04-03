package com.venhancer.employee.management.system.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.venhancer.employee.management.system.DTO.ManagerDTO;
import com.venhancer.employee.management.system.Service.ManagerService;

@RestController
@RequestMapping("/api/ems/manager")
public class ManagerController {

    @Autowired
    ManagerService managerService;

    @PostMapping
    public ResponseEntity<ManagerDTO> createManager(@RequestBody ManagerDTO managerDTO){
        ManagerDTO createdManager = managerService.createManager(managerDTO);
        return new ResponseEntity<>(createdManager, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManagerDTO> getManager(@PathVariable Long id){
        ManagerDTO manager = managerService.getManagerById(id);
        return ResponseEntity.ok(manager);
    }

    @GetMapping
    public ResponseEntity<List<ManagerDTO>> getAllManagers(){
        List<ManagerDTO> managers = managerService.getAllManagers();
        return ResponseEntity.ok(managers);
    }

    @GetMapping("/department/{departmentName}")
    public ResponseEntity<ManagerDTO> getManagerByDepartmentName (@PathVariable String departmentName){
        ManagerDTO manager = managerService.getManagerByDepartmentName(departmentName);
        return ResponseEntity.ok(manager);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ManagerDTO> updateManagerDepartment(@PathVariable Long id, @RequestBody ManagerDTO managerDTO){
        ManagerDTO updatedManager = managerService.updateManagerDepartment(id, managerDTO);
        return ResponseEntity.ok(updatedManager);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteManager(@PathVariable Long id) {
        managerService.deleteManager(id);
        return ResponseEntity.ok("Manager successfully deleted!");
    }
}
