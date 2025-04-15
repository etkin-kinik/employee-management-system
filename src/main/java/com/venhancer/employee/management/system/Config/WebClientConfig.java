package com.venhancer.employee.management.system.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient weatherWebClient(){
        return WebClient.builder().baseUrl("https://wttr.in").build();
    }

}
