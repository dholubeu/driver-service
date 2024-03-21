package com.dholubeu.driverservice.service.impl;

import com.dholubeu.driverservice.domain.*;
import com.dholubeu.driverservice.domain.exception.IllegalOperationException;
import com.dholubeu.driverservice.domain.exception.ResourceAlreadyExistsException;
import com.dholubeu.driverservice.domain.exception.ResourceDoesNotExistException;
import com.dholubeu.driverservice.domain.Status;
import com.dholubeu.driverservice.repository.DriverRepository;
import com.dholubeu.driverservice.service.CoordinateService;
import com.dholubeu.driverservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    public static final String RESOURCE_ALREADY_EXISTS_MESSAGE = "Driver with email %s already exists";
    public static final String RESOURCE_DOES_NOT_EXIST_BY_ID_MESSAGE = "Driver with id %d does not exist";
    public static final String RESOURCE_DOES_NOT_EXIST_BY_EMAIL_MESSAGE = "Driver with email %s does not exist";
    public static final String ILLEGAL_OPERATION_EXCEPTION_MESSAGE =
            "Driver with id %d is not activated";

    private final DriverRepository driverRepository;
    private final CoordinateService coordinateService;

    @Override
    public Driver create(Driver driver) {
        if (driverRepository.existsByEmail(driver.getEmail())) {
            throw new ResourceAlreadyExistsException(String.format(
                    RESOURCE_ALREADY_EXISTS_MESSAGE, driver.getEmail()));
        }
        driver.setStatus(Status.OFF_DUTY);
        driver.setRating(BigDecimal.valueOf(0.0));
        driver.setBalance(BigDecimal.valueOf(0.0));
        return driverRepository.save(driver);
    }

    @Override
    public Driver findById(Long id) {
        return driverRepository.findById(id).orElseThrow(
                () -> new ResourceDoesNotExistException(
                        String.format(RESOURCE_DOES_NOT_EXIST_BY_ID_MESSAGE, id)));
    }

    @Override
    public List<Driver> findNearest(BigDecimal longitude, BigDecimal latitude) {
        List<Driver> drivers = driverRepository.findAllByStatus(Status.AVAILABLE);
        Map<BigDecimal, Driver> distances = new TreeMap<>();
        for (Driver driver : drivers) {
            Coordinate coordinate = coordinateService.getCoordinates(
                    driver.getCar().getCurrentAddress());
            BigDecimal distance = coordinateService.calculateDistance(
                    coordinate.getLatitude(), coordinate.getLongitude(), latitude, longitude);
            distances.put(distance, driver);
        }
        List<Driver> nearestDrivers = new ArrayList<>(distances.values());
        if (nearestDrivers.size() > 10) {
            nearestDrivers = nearestDrivers.subList(0, 10);
        }
        return nearestDrivers;
    }

    @Override
    public Driver findByEmail(String email) {
        return driverRepository.findByEmail(email).orElseThrow(
                () -> new ResourceDoesNotExistException(
                        String.format(RESOURCE_DOES_NOT_EXIST_BY_EMAIL_MESSAGE, email)));
    }

    @Override
    public Driver activate(Long id) {
        Driver driver = findById(id);
        driver.setActivated(true);
        return driverRepository.save(driver);
    }

    @Override
    public Driver addCar(Long id, Car car) {
        Driver driver = findById(id);
        driver.setCar(car);
        return driverRepository.save(driver);
    }

    @Override
    public Driver addCard(Long id, Card card) {
        Driver driver = findById(id);
        driver.setCard(card);
        return driverRepository.save(driver);
    }

    @Override
    public Driver update(Long id, Driver driver) {
        Driver driverUpdated = findById(id);
        driverUpdated = Driver.builder()
                .id(driverUpdated.getId())
                .email(driverUpdated.getEmail())
                .name(driver.getName())
                .surname(driver.getSurname())
                .dateOfBirth(driver.getDateOfBirth())
                .phoneNumber(driver.getPhoneNumber())
                .isActivated(driverUpdated.isActivated())
                .car(driverUpdated.getCar())
                .card(driverUpdated.getCard())
                .drivingExperience(driver.getDrivingExperience())
                .status(driverUpdated.getStatus())
                .balance(driverUpdated.getBalance())
                .build();
        return driverRepository.save(driverUpdated);
    }

    @Override
    public Driver updateStatus(Long id, Status status) {
        Driver driver = findById(id);
        if (!driver.isActivated()) {
            throw new IllegalOperationException(String.format(
                    ILLEGAL_OPERATION_EXCEPTION_MESSAGE, id));
        }
        driver.setStatus(status);
        return driverRepository.save(driver);
    }

    @Override
    public Driver updateRating(Long id, BigDecimal rating) {
        Driver driver = findById(id);
        driver.setRating(rating);
        return driverRepository.save(driver);
    }

    @Override
    public Driver updateBalance(Long id, BigDecimal newBalance) {
        Driver driver = findById(id);
        driver.setBalance(newBalance);
        return driverRepository.save(driver);
    }

}