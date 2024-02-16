package com.dholubeu.driverservice.web.controller;

import com.dholubeu.driverservice.domain.Car;
import com.dholubeu.driverservice.domain.Card;
import com.dholubeu.driverservice.domain.Driver;
import com.dholubeu.driverservice.service.CarService;
import com.dholubeu.driverservice.service.CardService;
import com.dholubeu.driverservice.service.DriverService;
import com.dholubeu.driverservice.service.MinioService;
import com.dholubeu.driverservice.web.dto.CarDto;
import com.dholubeu.driverservice.web.dto.CardDto;
import com.dholubeu.driverservice.web.dto.DriverDto;
import com.dholubeu.driverservice.web.dto.validation.OnCreate;
import com.dholubeu.driverservice.web.dto.validation.OnUpdate;
import com.dholubeu.driverservice.web.mapper.CarMapper;
import com.dholubeu.driverservice.web.mapper.CardMapper;
import com.dholubeu.driverservice.web.mapper.DriverMapper;
import com.dholubeu.driverservice.web.validator.ValidFile;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/api/v1/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;
    private final CardService cardService;
    private final CarService carService;
    private final MinioService minioService;
    private final DriverMapper driverMapper;
    private final CardMapper cardMapper;
    private final CarMapper carMapper;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public DriverDto create(@RequestBody @Validated(OnCreate.class) DriverDto driverDto) {
        Driver driver = driverMapper.toEntity(driverDto);
        driver = driverService.create(driver);
        return driverMapper.toDto(driver);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DriverDto findById(@PathVariable Long id) {
        Driver driver = driverService.findById(id);
        return driverMapper.toDto(driver);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<DriverDto> findNearest(@RequestParam BigDecimal latitude,
                                       @RequestParam BigDecimal longitude) {
        List<Driver> drivers = driverService.findNearest(latitude, longitude);
        return driverMapper.toDto(drivers);
    }

    @PutMapping("/activate/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DriverDto activate(@PathVariable Long id) {
        Driver driver = driverService.activate(id);
        return driverMapper.toDto(driver);
    }

    @PostMapping("/{id}/cards")
    @ResponseStatus(HttpStatus.CREATED)
    public CardDto createCard(@PathVariable Long id,
                              @RequestBody @Validated(OnCreate.class) CardDto cardDto) {
        Card card = cardMapper.toEntity(cardDto);
        card = cardService.create(id, card);
        return cardMapper.toDto(card);
    }

    @PostMapping("/{id}/cars")
    @ResponseStatus(HttpStatus.CREATED)
    public CarDto createCar(@PathVariable Long id,
                            @RequestBody @Validated(OnCreate.class) CarDto carDto) {
        Car car = carMapper.toEntity(carDto);
        car = carService.create(id, car);
        return carMapper.toDto(car);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DriverDto update(@PathVariable Long id,
                            @RequestBody @Validated(OnUpdate.class) DriverDto driverDto) {
        Driver driver = driverMapper.toEntity(driverDto);
        driver = driverService.update(id, driver);
        return driverMapper.toDto(driver);
    }

    @PutMapping("/{id}/statuses")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DriverDto updateStatus(@PathVariable Long id,
                                  @RequestParam Driver.Status status) {
        Driver driver = driverService.updateStatus(id, status);
        return driverMapper.toDto(driver);
    }

    @PutMapping("/{id}/ratings")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DriverDto updateRating(@PathVariable Long id,
                                  @RequestParam BigDecimal rating) {
        Driver driver = driverService.updateRating(id, rating);
        return driverMapper.toDto(driver);
    }

    @PutMapping("/{driverId}/cards/{cardId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CardDto updateBalance(@PathVariable Long driverId, @PathVariable Long cardId,
                                 @RequestParam BigDecimal amount) {
        Card card = cardService.updateBalance(driverId, cardId, amount);
        return cardMapper.toDto(card);
    }

    @DeleteMapping("/cards/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteCard(@PathVariable Long id) {
        cardService.delete(id);
    }

    @GetMapping("/cards/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CardDto findCardById(@PathVariable Long id) {
        Card card = cardService.findById(id);
        return cardMapper.toDto(card);
    }

    @PutMapping("/cars/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CarDto updateCurrentAddress(@PathVariable Long id,
                                       @RequestParam String currentAddress) {
        Car car = carService.updateCurrentAddress(id, currentAddress);
        return carMapper.toDto(car);
    }

    @DeleteMapping("/cars/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteCar(@PathVariable Long id) {
        carService.delete(id);
    }

    @GetMapping("/cars/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CarDto findCarById(@PathVariable Long id) {
        Car car = carService.findById(id);
        return carMapper.toDto(car);
    }

    @PostMapping("/{id}/files")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadDocuments(@PathVariable Long id,
                                @RequestParam @ValidFile MultipartFile file) {
        minioService.uploadDocuments(id, file);
    }

}