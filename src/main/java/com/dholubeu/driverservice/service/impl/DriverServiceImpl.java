package com.dholubeu.driverservice.service.impl;

import com.dholubeu.driverservice.domain.Driver;
import com.dholubeu.driverservice.domain.Car;
import com.dholubeu.driverservice.domain.Coordinate;
import com.dholubeu.driverservice.domain.Card;
import com.dholubeu.driverservice.domain.exception.IllegalOperationException;
import com.dholubeu.driverservice.domain.exception.ResourceAlreadyExistsException;
import com.dholubeu.driverservice.domain.exception.ResourceDoesNotExistException;
import com.dholubeu.driverservice.domain.Status;
import com.dholubeu.driverservice.repository.DriverRepository;
import com.dholubeu.driverservice.service.CoordinateService;
import com.dholubeu.driverservice.service.DriverService;
import com.dholubeu.driverservice.web.dto.DriverDto;
import com.dholubeu.driverservice.web.mapper.DriverMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;

import static com.dholubeu.driverservice.util.Constants.DRIVER_DOES_NOT_EXIST_BY_ID_MESSAGE;
import static com.dholubeu.driverservice.util.Constants.DRIVER_ALREADY_EXISTS_MESSAGE;
import static com.dholubeu.driverservice.util.Constants.DRIVER_DOES_NOT_EXIST_BY_EMAIL_MESSAGE;
import static com.dholubeu.driverservice.util.Constants.ILLEGAL_OPERATION_EXCEPTION_MESSAGE;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;
    private final CoordinateService coordinateService;

    @Override
    public DriverDto create(DriverDto driverDto) {
        if (driverRepository.existsByEmail(driverDto.getEmail())) {
            throw new ResourceAlreadyExistsException(String.format(
                    DRIVER_ALREADY_EXISTS_MESSAGE, driverDto.getEmail()));
        }
        Driver driver = driverMapper.toEntity(driverDto);
        driver.setStatus(Status.OFF_DUTY);
        driver.setRating(BigDecimal.valueOf(0.0));
        driver.setBalance(BigDecimal.valueOf(0.0));
        return driverMapper.toDto(driver);
    }

    @Override
    public DriverDto findById(Long id) {
        Driver driver = driverRepository.findById(id).orElseThrow(
                () -> new ResourceDoesNotExistException(
                        String.format(DRIVER_DOES_NOT_EXIST_BY_ID_MESSAGE, id)));
        return driverMapper.toDto(driver);
    }

    @Override
    public List<DriverDto> findNearest(BigDecimal longitude, BigDecimal latitude) {
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
        return driverMapper.toDto(nearestDrivers);
    }

    @Override
    public DriverDto findByEmail(String email) {
        Driver driver = driverRepository.findByEmail(email).orElseThrow(
                () -> new ResourceDoesNotExistException(
                        String.format(DRIVER_DOES_NOT_EXIST_BY_EMAIL_MESSAGE, email)));
        return driverMapper.toDto(driver);
    }

    @Override
    public DriverDto activate(Long id) {
        Driver driver = driverRepository.findById(id).orElseThrow(
                () -> new ResourceDoesNotExistException(
                        String.format(DRIVER_DOES_NOT_EXIST_BY_ID_MESSAGE, id)));
        driver.setActivated(true);
        return driverMapper.toDto(driver);
    }

    @Override
    public DriverDto addCar(Long id, Car car) {
        Driver driver = driverRepository.findById(id).orElseThrow(
                () -> new ResourceDoesNotExistException(
                        String.format(DRIVER_DOES_NOT_EXIST_BY_ID_MESSAGE, id)));
        driver.setCar(car);
        return driverMapper.toDto(driver);
    }

    @Override
    public DriverDto addCard(Long id, Card card) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException(
                        String.format(DRIVER_DOES_NOT_EXIST_BY_ID_MESSAGE, id)));
        driver.setCard(card);
        return driverMapper.toDto(driver);
    }

    @Override
    public DriverDto update(Long id, DriverDto driverDto) {
        Driver driver = driverMapper.toEntity(driverDto);
        Driver driverUpdated = driverRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException(
                        String.format(DRIVER_DOES_NOT_EXIST_BY_ID_MESSAGE, id)));
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
        driverRepository.save(driverUpdated);
        return driverMapper.toDto(driver);
    }

    @Override
    public DriverDto updateStatus(Long id, Status status) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException(
                        String.format(DRIVER_DOES_NOT_EXIST_BY_ID_MESSAGE, id)));
        if (!driver.isActivated()) {
            throw new IllegalOperationException(String.format(
                    ILLEGAL_OPERATION_EXCEPTION_MESSAGE, id));
        }
        driver.setStatus(status);
        driverRepository.save(driver);
        return driverMapper.toDto(driver);
    }

    @Override
    public DriverDto updateRating(Long id, BigDecimal rating) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException(
                        String.format(DRIVER_DOES_NOT_EXIST_BY_ID_MESSAGE, id)));
        driver.setRating(rating);
        driverRepository.save(driver);
        return driverMapper.toDto(driver);
    }

    @Override
    public DriverDto updateBalance(Long id, BigDecimal newBalance) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException(""));
        driver.setBalance(newBalance);
        driverRepository.save(driver);
        return driverMapper.toDto(driver);
    }

}