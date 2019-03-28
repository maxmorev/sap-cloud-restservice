package ru.maxmorev.cloud.sap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.maxmorev.cloud.sap.service.OpenweatherTemperatureProvider;
import ru.maxmorev.cloud.sap.service.TemperatureProvider;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public TemperatureProvider temperatureProvider(){
        return new OpenweatherTemperatureProvider();
    }
}
