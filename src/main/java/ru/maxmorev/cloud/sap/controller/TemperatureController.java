package ru.maxmorev.cloud.sap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import ru.maxmorev.cloud.sap.entity.CityTemperature;
import ru.maxmorev.cloud.sap.repository.CityTemperatureRepository;
import ru.maxmorev.cloud.sap.request.TemperatureRequest;
import ru.maxmorev.cloud.sap.response.TemparatureResponse;

@RestController
public class TemperatureController {

    //private static final Logger log = LoggerFactory.getLogger(TemperatureController.class);

    //http://api.openweathermap.org/data/2.5/weather?q=Moscow&APPID=82005a68994fa324046a7c4fb9006359
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static final String API_KEY = "&APPID=82005a68994fa324046a7c4fb9006359";
    private static final String API_METRICS_CELSIUS = "&units=metric";

    private static final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    CityTemperatureRepository cityTemperatureRepository;

    @RequestMapping(value = "/temperature/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public TemparatureResponse getTemperature(
            @RequestParam(value="cityName", required = true) String cityName
    )
    {
    	
    	cityName = cityName.trim().toLowerCase();
        CityTemperature tempCheck = cityTemperatureRepository.findByCity(cityName);


        if(tempCheck==null) {
            StringBuilder apiOpenweatherRequest = new StringBuilder(TemperatureController.API_URL);
            apiOpenweatherRequest
                    .append(cityName)
                    .append(TemperatureController.API_METRICS_CELSIUS)
                    .append(TemperatureController.API_KEY);
            TemperatureRequest temperatureRequest = restTemplate.getForObject(apiOpenweatherRequest.toString(), TemperatureRequest.class);
            CityTemperature cityTemperature = new CityTemperature(cityName, temperatureRequest.getMain().getTemp());
            cityTemperatureRepository.save(cityTemperature);
            //log.info("SAVE cityTemperature " + cityTemperature);
            return new TemparatureResponse( temperatureRequest.getMain().getTemp() );
        }else{
            //log.info("FOUND cityTemperature " + tempCheck);
            return  new TemparatureResponse(tempCheck.getTemperature());
        }

    }
    
    /**
     * Исключаем все методы запросов и возврящаем HttpStatus.METHOD_NOT_ALLOWED
     * @return 405 Method Not Allowed
     */
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.PATCH  })
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED) 
    public String excludeAllOtherMethods() {
        return "405 Method Not Allowed";
    }
    



}
