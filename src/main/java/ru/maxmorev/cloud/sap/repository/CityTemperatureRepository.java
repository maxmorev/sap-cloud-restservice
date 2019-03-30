package ru.maxmorev.cloud.sap.repository;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;
import ru.maxmorev.cloud.sap.entity.CityTemperature;

public interface CityTemperatureRepository extends CrudRepository<CityTemperature, Long> {


    CityTemperature findByCity(String city);


}
