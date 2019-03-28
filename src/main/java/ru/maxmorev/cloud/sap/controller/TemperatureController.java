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
import ru.maxmorev.cloud.sap.service.OpenweatherTemperatureProvider;
import ru.maxmorev.cloud.sap.service.TemperatureProvider;

@RestController
public class TemperatureController {

    @Autowired
    CityTemperatureRepository cityTemperatureRepository;

    TemperatureProvider temperatureProvider = new OpenweatherTemperatureProvider();
    
    /**
     * takes the name of the city and as a result returns (by a synchronous answer) the current temperature in the transferred city.
     * The received data is saved in in-memory db H2. 
     * On subsequent accessing, check that there is data in the database, and in the absence of a REST request.
     * @param cityName название города
     * @return
     */
    @RequestMapping(value = "/temperature/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public TemparatureResponse getTemperature(
            @RequestParam(value="cityName", required = true) String cityName
    )
    {
    	if(cityName.trim().isEmpty()) {
    		throw new IllegalArgumentException("Parameter cityName must be not empty");
    	}
   
        if( !cityName.chars().allMatch(Character::isLetter) ){ //if cityName contains wrong symbols
        	throw new IllegalArgumentException("Incorrect content of parameter cityName");
        }
    	
    	cityName = cityName.trim().toLowerCase();
        CityTemperature cityTempPresent = cityTemperatureRepository.findByCity(cityName);


        if(cityTempPresent==null) {

            float temperature = temperatureProvider.getTemperatureByCityName(cityName);
            CityTemperature cityTemperature = new CityTemperature(cityName, temperature );
            cityTemperatureRepository.save(cityTemperature);

            return new TemparatureResponse( temperature );
        }else{
            //log.info("FOUND cityTemperature " + tempCheck);
            return  new TemparatureResponse(cityTempPresent.getTemperature());
        }

    }
    
    /**
     * Exclude all query methods except GET and return HttpStatus.METHOD_NOT_ALLOWED
     * @return 405 Method Not Allowed
     */
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.PATCH  })
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED) 
    public String excludeAllOtherMethods() {
        return "405 Method Not Allowed";
    }
    



}
