package com.venhancer.employee.management.system.Entity;

import java.util.List;

import lombok.Data;

@Data
public class Weather {

    private List<CurrentCondition> current_condition;

    @Data
    public static class CurrentCondition {
        private String temp_C;
        private String humidity;
        private List<WeatherDescription> weatherDesc;
    }

    @Data
    public static class WeatherDescription{
        private String value;
    }
}
