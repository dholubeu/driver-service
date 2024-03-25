package com.dholubeu.driverservice.service.impl;

import com.dholubeu.driverservice.domain.Card;
import com.dholubeu.driverservice.domain.Driver;
import com.dholubeu.driverservice.domain.exception.IllegalOperationException;
import com.dholubeu.driverservice.domain.exception.ResourceDoesNotExistException;
import com.dholubeu.driverservice.repository.CardRepository;
import com.dholubeu.driverservice.repository.DriverRepository;
import com.dholubeu.driverservice.service.CardService;
import com.dholubeu.driverservice.service.DriverService;
import com.dholubeu.driverservice.web.dto.CardDto;
import com.dholubeu.driverservice.web.mapper.CardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.dholubeu.driverservice.util.Constants.ILLEGAL_OPERATION_EXCEPTION_MESSAGE;
import static com.dholubeu.driverservice.util.Constants.RESOURCE_DOES_NOT_EXIST_BY_ID_MESSAGE;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final DriverRepository driverRepository;

    private final CardMapper cardMapper;
    private final DriverService driverService;

    @Override
    public CardDto findById(Long id) {
        var card = cardRepository.findById(id).orElseThrow(
                () -> new ResourceDoesNotExistException(
                        String.format(RESOURCE_DOES_NOT_EXIST_BY_ID_MESSAGE, id)));
        return cardMapper.toDto(card);
    }

    @Override
    public CardDto create(Long driverId, CardDto cardDto) {
        Card card = cardMapper.toEntity(cardDto);
        card.setBalance(BigDecimal.valueOf(0.0));
        card = cardRepository.save(card);
        driverService.addCard(driverId, card);
        return cardMapper.toDto(card);
    }

    @Override
    public CardDto updateBalance(Long driverId, Long cardId, BigDecimal amount) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new ResourceDoesNotExistException(""));

        var card = cardRepository.findById(cardId).orElseThrow(
                () -> new ResourceDoesNotExistException(
                        String.format(RESOURCE_DOES_NOT_EXIST_BY_ID_MESSAGE, cardId)));

        if (driver.getBalance().compareTo(amount) < 0) {
            throw new IllegalOperationException(String.format(
                    ILLEGAL_OPERATION_EXCEPTION_MESSAGE, amount));
        }
        BigDecimal newBalance = driver.getBalance().subtract(amount);
        driverService.updateBalance(driver.getId(), newBalance);
        card.setBalance(card.getBalance().add(amount));

        cardRepository.save(card);
        return cardMapper.toDto(card);
    }

    @Override
    public void delete(Long id) {
        cardRepository.deleteById(id);
    }

}
