package com.dholubeu.driverservice.service;

import com.dholubeu.driverservice.domain.Car;
import com.dholubeu.driverservice.web.dto.CarDto;

public interface CarService {

    CarDto findById(Long id);

    CarDto create(Long driverId, CarDto carDto);

    CarDto updateCurrentAddress(Long id, String currentAddress);

    void delete(Long id);

}