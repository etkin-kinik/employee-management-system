package com.venhancer.employee.management.system.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.venhancer.employee.management.system.DTO.UsersDTO;
import com.venhancer.employee.management.system.Entity.Users;
import com.venhancer.employee.management.system.Service.UsersService;


@RestController
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/register")
    public ResponseEntity<UsersDTO> registerUser(@RequestBody UsersDTO usersdDto){
        UsersDTO users = usersService.registerUser(usersdDto);
        return new ResponseEntity<>(users, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public String login(@RequestBody Users users){
        return usersService.verify(users);
    }
}
