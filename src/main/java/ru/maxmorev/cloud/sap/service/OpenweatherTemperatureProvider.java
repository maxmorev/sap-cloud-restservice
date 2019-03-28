package ru.maxmorev.cloud.sap.service;

import org.springframework.web.client.RestTemplate;
import ru.maxmorev.cloud.sap.request.TemperatureRequest;

public class OpenweatherTemperatureProvider implements TemperatureProvider {

    //http://api.openweathermap.org/data/2.5/weather?q=Moscow&APPID=82005a68994fa324046a7c4fb9006359
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?APPID=82005a68994fa324046a7c4fb9006359&units=metric&q=";
    private static final String API_KEY = "82005a68994fa324046a7c4fb9006359";
    private static final String API_METRICS_CELSIUS = "units=metric";

    private static final RestTemplate restTemplate = new RestTemplate();

    @Override
    public float getTemperatureByCityName(String cityName) {

        StringBuilder apiOpenweatherRequest =
                                    new StringBuilder(OpenweatherTemperatureProvider.API_URL)
                                    .append(cityName);
        TemperatureRequest temperatureRequest = restTemplate.getForObject(apiOpenweatherRequest.toString(), TemperatureRequest.class);

        return temperatureRequest.getMain().getTemp();
    }
}
