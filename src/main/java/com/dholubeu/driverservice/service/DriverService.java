package com.dholubeu.driverservice.service;

import com.dholubeu.driverservice.domain.Car;
import com.dholubeu.driverservice.domain.Card;
import com.dholubeu.driverservice.domain.Driver;
import com.dholubeu.driverservice.domain.Driver.Status;

import java.math.BigDecimal;
import java.util.List;

public interface DriverService {

    Driver create(Driver driver);

    Driver findById(Long id);

    List<Driver> findNearest(BigDecimal longitude, BigDecimal latitude);

    Driver findByEmail(String email);

    Driver activate(Long id);

    Driver addCar(Long id, Car car);

    Driver addCard(Long id, Card card);

    Driver update(Long id, Driver driver);

    Driver updateStatus(Long id, Status status);

    Driver updateRating(Long id, BigDecimal rating);

    Driver updateBalance(Long id, BigDecimal newBalance);

}