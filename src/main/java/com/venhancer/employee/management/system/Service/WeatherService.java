package com.venhancer.employee.management.system.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.venhancer.employee.management.system.Entity.Weather;


@Service
public class WeatherService {

    @Autowired
    private WebClient weatherWebClient;

    public Weather getWeatherByCity(String city){
        return weatherWebClient.get().uri(uri -> uri.path("/" + city)
                        .queryParam("format", "j1")
                        .build())
                    .retrieve().bodyToMono(Weather.class).block();
    }

}
