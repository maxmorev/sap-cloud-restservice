package ru.maxmorev.cloud.sap.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TemperatureRequest {

    private TemperatureInfo main;

    public TemperatureInfo getMain() {
        return main;
    }

    public void setMain(TemperatureInfo main) {
        this.main = main;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();

        String jsonStr = "";
        try {
            jsonStr = mapper.writeValueAsString(this);
        }catch(Exception e) {

        }
        return jsonStr;
    }

}
