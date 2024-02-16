package com.dholubeu.driverservice.service.impl;

import com.dholubeu.driverservice.domain.Card;
import com.dholubeu.driverservice.domain.Driver;
import com.dholubeu.driverservice.domain.exception.IllegalOperationException;
import com.dholubeu.driverservice.domain.exception.ResourceDoesNotExistException;
import com.dholubeu.driverservice.repository.CardRepository;
import com.dholubeu.driverservice.service.CardService;
import com.dholubeu.driverservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    public static final String ILLEGAL_OPERATION_EXCEPTION_MESSAGE =
            "You have insufficient funds to transfer amount %d to the card";
    public static final String RESOURCE_DOES_NOT_EXIST_BY_ID_MESSAGE = "Card with id %d does not exist";

    private final CardRepository cardRepository;
    private final DriverService driverService;

    @Override
    public Card findById(Long id) {
        return cardRepository.findById(id).orElseThrow(
                () -> new ResourceDoesNotExistException(
                        String.format(RESOURCE_DOES_NOT_EXIST_BY_ID_MESSAGE, id)));
    }

    @Override
    public Card create(Long driverId, Card card) {
        card.setBalance(BigDecimal.valueOf(0.0));
        card =  cardRepository.save(card);
        driverService.addCard(driverId, card);
        return card;
    }

    @Override
    public Card updateBalance(Long driverId, Long cardId, BigDecimal amount) {
        Driver driver = driverService.findById(driverId);
        Card card = findById(cardId);
        if (driver.getBalance().compareTo(amount) < 0) {
            throw new IllegalOperationException(String.format(
                    ILLEGAL_OPERATION_EXCEPTION_MESSAGE, amount));
        }
        BigDecimal newBalance = driver.getBalance().subtract(amount);
        driverService.updateBalance(driver.getId(), newBalance);
        card.setBalance(card.getBalance().add(amount));
        return cardRepository.save(card);
    }

    @Override
    public void delete(Long id) {
        cardRepository.deleteById(id);
    }

}
