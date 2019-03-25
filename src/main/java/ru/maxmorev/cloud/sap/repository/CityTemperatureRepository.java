package ru.maxmorev.cloud.sap.repository;

import org.springframework.data.repository.CrudRepository;

import ru.maxmorev.cloud.sap.entity.CityTemperature;

public interface CityTemperatureRepository extends CrudRepository<CityTemperature, Long> {


    CityTemperature findByCity(String city);


}
