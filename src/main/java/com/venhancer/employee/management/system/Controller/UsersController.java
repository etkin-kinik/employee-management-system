package com.venhancer.employee.management.system.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.venhancer.employee.management.system.Entity.Users;
import com.venhancer.employee.management.system.Service.UsersService;


@RestController
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/register")
    public Users registerUser(@RequestBody Users users){
        return usersService.registerUser(users);
    }

    @PostMapping("/login")
    public String login(@RequestBody Users users){
        return usersService.verify(users);
    }
}
