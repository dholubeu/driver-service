package com.dholubeu.driverservice.service.impl;

import com.dholubeu.driverservice.domain.Car;
import com.dholubeu.driverservice.domain.exception.ResourceAlreadyExistsException;
import com.dholubeu.driverservice.domain.exception.ResourceDoesNotExistException;
import com.dholubeu.driverservice.repository.CarRepository;
import com.dholubeu.driverservice.service.CarService;
import com.dholubeu.driverservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    public static final String RESOURCE_ALREADY_EXISTS_MESSAGE =
            "Car with number %s has been already registered";
    public static final String RESOURCE_DOES_NOT_EXIST_BY_ID_MESSAGE = "Car with id %d does not exist";

    private final CarRepository carRepository;
    private final DriverService driverService;

    @Override
    public Car create(Long driverId, Car car) {
        if (carRepository.existsByNumber(car.getNumber())) {
            throw new ResourceAlreadyExistsException(String.format(
                    RESOURCE_ALREADY_EXISTS_MESSAGE, car.getNumber()));
        }
        car = carRepository.save(car);
        driverService.addCar(driverId, car);
        return car;
    }

    @Override
    public Car findById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException(
                        String.format(RESOURCE_DOES_NOT_EXIST_BY_ID_MESSAGE, id)));
    }

    @Override
    public Car updateCurrentAddress(Long id, String currentAddress) {
        Car car = findById(id);
        car.setCurrentAddress(currentAddress);
        return carRepository.save(car);
    }

    @Override
    public void delete(Long id) {
        carRepository.deleteById(id);
    }

}