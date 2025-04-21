package com.venhancer.employee.management.system.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.venhancer.employee.management.system.Entity.Address;
import com.venhancer.employee.management.system.Entity.Users;
import com.venhancer.employee.management.system.Entity.Weather;
import com.venhancer.employee.management.system.Repository.UsersRepository;


@Service
public class WeatherService {

    @Autowired
    private WebClient weatherWebClient;

    @Autowired
    UsersRepository usersRepository;

    public Weather getWeatherByCity(){
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        String name = authenticatedUser.getName();

        Users user = usersRepository.findByUsername(name);

        String city;

        if(user.getEmployee() != null){
            Address employeeAddress = user.getEmployee().getEmployeeProfile().getAddress();
            city = employeeAddress.getCity();
        } else if (user.getManager() != null){
            Address managerAddress = user.getManager().getManagerProfile().getAddress();
            city = managerAddress.getCity();
        } else {
            throw new RuntimeException("User is neither an employee nor a manager");
        }

        return weatherWebClient.get().uri(uri -> uri.path("/" + city)
                        .queryParam("format", "j1")
                        .build())
                    .retrieve().bodyToMono(Weather.class).block();
    }
}