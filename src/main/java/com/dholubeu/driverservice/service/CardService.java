package com.dholubeu.driverservice.service;

import com.dholubeu.driverservice.domain.Card;

import java.math.BigDecimal;

public interface CardService {

    Card findById(Long id);

    Card create(Long driverId, Card card);

    Card updateBalance(Long driverId, Long cardId, BigDecimal amount);

    void delete(Long id);

}