package com.dholubeu.driverservice.service.impl;

import com.dholubeu.driverservice.domain.Car;
import com.dholubeu.driverservice.domain.Driver;
import com.dholubeu.driverservice.domain.exception.ResourceAlreadyExistsException;
import com.dholubeu.driverservice.domain.exception.ResourceDoesNotExistException;
import com.dholubeu.driverservice.repository.CarRepository;
import com.dholubeu.driverservice.service.CarService;
import com.dholubeu.driverservice.service.DriverService;
import com.dholubeu.driverservice.web.dto.CarDto;
import com.dholubeu.driverservice.web.mapper.CarMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.dholubeu.driverservice.util.Constants.RESOURCE_ALREADY_EXISTS_MESSAGE;
import static com.dholubeu.driverservice.util.Constants.RESOURCE_DOES_NOT_EXIST_BY_ID_MESSAGE;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {


    private final CarRepository carRepository;
    private final DriverService driverService;
    private final CarMapper carMapper;

    @Override
    public CarDto create(Long driverId, CarDto carDto) {
        if (carRepository.existsByNumber(carDto.getNumber())) {
            throw new ResourceAlreadyExistsException(String.format(
                    RESOURCE_ALREADY_EXISTS_MESSAGE, carDto.getNumber()));
        }
        Car car = carMapper.toEntity(carDto);
        car = carRepository.save(car);
        driverService.addCar(driverId, car);
        return carMapper.toDto(car);
    }

    @Override
    public CarDto findById(Long id) {
        var car= carRepository.findById(id).orElseThrow(
                () -> new ResourceDoesNotExistException(
                        String.format(RESOURCE_DOES_NOT_EXIST_BY_ID_MESSAGE, id)));
        return carMapper.toDto(car);
    }

    @Override
    public CarDto updateCurrentAddress(Long id, String currentAddress) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException(""));
        car.setCurrentAddress(currentAddress);
        carRepository.save(car);
        return carMapper.toDto(car);
    }

    @Override
    public void delete(Long id) {
        carRepository.deleteById(id);
    }

}