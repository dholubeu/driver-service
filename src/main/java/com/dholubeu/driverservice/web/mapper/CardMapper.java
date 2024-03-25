package com.dholubeu.driverservice.web.mapper;

import com.dholubeu.driverservice.domain.Card;
import com.dholubeu.driverservice.web.dto.CardDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardMapper {

    CardDto toDto(Card card);

    Card toEntity(CardDto cardDto);

}