package com.dholubeu.driverservice.web.mapper;

import com.dholubeu.driverservice.domain.Car;
import com.dholubeu.driverservice.web.dto.CarDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarMapper {

    CarDto toDto(Car car);

    Car toEntity(CarDto carDto);

}