package com.dholubeu.driverservice.service;

import com.dholubeu.driverservice.domain.Car;
import com.dholubeu.driverservice.domain.Card;
import com.dholubeu.driverservice.domain.Driver;
import com.dholubeu.driverservice.domain.Status;
import com.dholubeu.driverservice.web.dto.DriverDto;

import java.math.BigDecimal;
import java.util.List;

public interface DriverService {

    DriverDto create(DriverDto driverDto);

    DriverDto findById(Long id);

    List<DriverDto> findNearest(BigDecimal longitude, BigDecimal latitude);

    DriverDto findByEmail(String email);

    DriverDto activate(Long id);

    DriverDto addCar(Long id, Car car);

    DriverDto addCard(Long id, Card card);

    DriverDto update(Long id, DriverDto driverDto);

    DriverDto updateStatus(Long id, Status status);

    DriverDto updateRating(Long id, BigDecimal rating);

    DriverDto updateBalance(Long id, BigDecimal newBalance);

}