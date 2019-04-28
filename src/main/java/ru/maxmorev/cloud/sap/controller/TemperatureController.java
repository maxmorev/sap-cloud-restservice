package ru.maxmorev.cloud.sap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.maxmorev.cloud.sap.entity.CityTemperature;
import ru.maxmorev.cloud.sap.repository.CityTemperatureRepository;
import ru.maxmorev.cloud.sap.response.TemparatureResponse;
import ru.maxmorev.cloud.sap.service.TemperatureProvider;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@RestController
public class TemperatureController {

    private static final Logger logger = Logger.getLogger(TemperatureController.class.getName());

    //@Autowired
    CityTemperatureRepository cityTemperatureRepository;
    //@Autowired
    TemperatureProvider temperatureProvider;
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

            long difference = TimeUnit.MILLISECONDS.toMinutes( (new Date()).getTime()- cityTempPresent.getCreationDate().getTime());
            logger.info("FOUND cityTemperature difference = " + difference);
            if( difference > 10 ){
                float temperature = temperatureProvider.getTemperatureByCityName(cityName);
                cityTempPresent.setTemperature(temperature);
                cityTempPresent.setCreationDate( new Date() );
                cityTemperatureRepository.save(cityTempPresent);
            }
            TemparatureResponse temparatureResponse = new TemparatureResponse(cityTempPresent.getTemperature());
            temparatureResponse.setDifference(difference);
            return temparatureResponse;
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

    @Autowired
    public void setCityTemperatureRepository(CityTemperatureRepository cityTemperatureRepository) {
        this.cityTemperatureRepository = cityTemperatureRepository;
    }
    @Autowired
    public void setTemperatureProvider(TemperatureProvider temperatureProvider) {
        this.temperatureProvider = temperatureProvider;
    }
}
