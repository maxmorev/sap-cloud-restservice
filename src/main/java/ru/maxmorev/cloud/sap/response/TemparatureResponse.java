package ru.maxmorev.cloud.sap.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TemparatureResponse {

    private Float temperature;

    public TemparatureResponse(){super();}

    public TemparatureResponse(Float temperature){
        super();
        this.temperature=temperature;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }
}
