package com.venhancer.employee.management.system.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.venhancer.employee.management.system.Entity.MyUsersDetails;
import com.venhancer.employee.management.system.Entity.Users;
import com.venhancer.employee.management.system.Repository.UsersRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UsersRepository usersRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Users user = usersRepository.findByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("User Not Found");
        }
        
        return new MyUsersDetails(user);
    }

}