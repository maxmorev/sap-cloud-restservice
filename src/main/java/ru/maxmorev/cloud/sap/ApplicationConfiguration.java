package ru.maxmorev.cloud.sap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.support.RegistrationPolicy;
import ru.maxmorev.cloud.sap.service.OpenweatherTemperatureProvider;
import ru.maxmorev.cloud.sap.service.TemperatureProvider;

@Configuration
@EnableMBeanExport(registration= RegistrationPolicy.IGNORE_EXISTING)
public class ApplicationConfiguration {

    @Bean
    public TemperatureProvider temperatureProvider(){
        return new OpenweatherTemperatureProvider();
    }
}
