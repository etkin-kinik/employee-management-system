package com.venhancer.employee.management.system.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.venhancer.employee.management.system.Filter.JwtFilter;
import com.venhancer.employee.management.system.Service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    MyUserDetailsService myUserDetailsService;
    
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        return httpSecurity.csrf(customizer -> customizer.disable())
                        .authorizeHttpRequests(request -> request
                            .requestMatchers("/api/ems/employee/profile/update/{id}").hasRole("EMPLOYEE")
                            .requestMatchers("/api/ems/department/create", 
                                                         "/api/ems/department/delete/{id}",
                                                         "/api/ems/employee/create",
                                                         "/api/ems/employee/all",
                                                         "/api/ems/employee/department-update/{id}",
                                                         "/api/ems/employee/delete/{id}",
                                                         "/api/ems/manager/create",
                                                         "/api/ems/manager/all",
                                                         "/api/ems/manager/update/{id}",
                                                         "/api/ems/manager/delete/{id}",
                                                         "delete/{id}").hasRole("ADMIN")
                            .requestMatchers("/api/ems/manager/**").hasAnyRole("ADMIN", "MANAGER")
                            .requestMatchers("/api/ems/employee/profile/**").hasAnyRole("EMPLOYEE", "ADMIN", "MANAGER")
                            .requestMatchers("/api/ems/employee/**").hasAnyRole("ADMIN", "MANAGER")
                            .requestMatchers("/api/ems/department/**").hasAnyRole("ADMIN", "MANAGER")
                            .requestMatchers("register", "login", "/api/weather").permitAll()
                            .anyRequest().authenticated())
                        .httpBasic(Customizer.withDefaults())
                        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                        .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(myUserDetailsService);
        return provider;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }
}