package com.dholubeu.driverservice.service;

import com.dholubeu.driverservice.web.dto.CardDto;

import java.math.BigDecimal;

public interface CardService {

    CardDto findById(Long id);

    CardDto create(Long driverId, CardDto cardDto);

    CardDto updateBalance(Long driverId, Long cardId, BigDecimal amount);

    void delete(Long id);

}