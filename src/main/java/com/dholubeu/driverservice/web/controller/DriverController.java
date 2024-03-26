package com.dholubeu.driverservice.web.controller;

import com.dholubeu.driverservice.domain.Status;
import com.dholubeu.driverservice.service.CarService;
import com.dholubeu.driverservice.service.CardService;
import com.dholubeu.driverservice.service.DriverService;
import com.dholubeu.driverservice.service.MinioService;
import com.dholubeu.driverservice.web.dto.CarDto;
import com.dholubeu.driverservice.web.dto.CardDto;
import com.dholubeu.driverservice.web.dto.DriverDto;
import com.dholubeu.driverservice.web.dto.validation.OnCreate;
import com.dholubeu.driverservice.web.dto.validation.OnUpdate;
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

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public DriverDto create(@RequestBody @Validated(OnCreate.class) DriverDto driverDto) {
        return driverService.create(driverDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DriverDto findById(@PathVariable Long id) {
        return driverService.findById(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<DriverDto> findNearest(@RequestParam BigDecimal latitude,
                                       @RequestParam BigDecimal longitude) {
        return driverService.findNearest(longitude, latitude);
    }

    @PutMapping("/activate/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DriverDto activate(@PathVariable Long id) {
        return driverService.activate(id);
    }

    @PostMapping("/{id}/cards")
    @ResponseStatus(HttpStatus.CREATED)
    public CardDto createCard(@PathVariable Long id,
                              @RequestBody @Validated(OnCreate.class) CardDto cardDto) {

        return cardService.create(id, cardDto);
    }

    @PostMapping("/{id}/cars")
    @ResponseStatus(HttpStatus.CREATED)
    public CarDto createCar(@PathVariable Long id,
                            @RequestBody @Validated(OnCreate.class) CarDto carDto) {
        return carService.create(id, carDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DriverDto update(@PathVariable Long id,
                            @RequestBody @Validated(OnUpdate.class) DriverDto driverDto) {
        return driverService.update(id, driverDto);
    }

    @PutMapping("/{id}/statuses")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DriverDto updateStatus(@PathVariable Long id,
                                  @RequestParam Status status) {
        return driverService.updateStatus(id, status);
    }

    @PutMapping("/{id}/ratings")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DriverDto updateRating(@PathVariable Long id,
                                  @RequestParam BigDecimal rating) {
        return driverService.updateRating(id, rating);
    }

    @PutMapping("/{driverId}/cards/{cardId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CardDto updateBalance(@PathVariable Long driverId, @PathVariable Long cardId,
                                 @RequestParam BigDecimal amount) {
        return cardService.updateBalance(driverId, cardId, amount);
    }

    @DeleteMapping("/cards/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteCard(@PathVariable Long id) {
        cardService.delete(id);
    }

    @GetMapping("/cards/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CardDto findCardById(@PathVariable Long id) {
        return cardService.findById(id);
    }

    @PutMapping("/cars/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CarDto updateCurrentAddress(@PathVariable Long id,
                                       @RequestParam String currentAddress) {
        return carService.updateCurrentAddress(id, currentAddress);
    }

    @DeleteMapping("/cars/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteCar(@PathVariable Long id) {
        carService.delete(id);
    }

    @GetMapping("/cars/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CarDto findCarById(@PathVariable Long id) {
        return carService.findById(id);
    }

    @PostMapping("/{id}/files")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadDocuments(@PathVariable Long id,
                                @RequestParam @ValidFile MultipartFile file) {
        minioService.uploadDocuments(id, file);
    }

}