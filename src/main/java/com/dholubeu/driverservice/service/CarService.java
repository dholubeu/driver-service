package com.dholubeu.driverservice.service;

import com.dholubeu.driverservice.domain.Car;

public interface CarService {

    Car findById(Long id);

    Car create(Long driverId, Car car);

    Car updateCurrentAddress(Long id, String currentAddress);

    void delete(Long id);

}