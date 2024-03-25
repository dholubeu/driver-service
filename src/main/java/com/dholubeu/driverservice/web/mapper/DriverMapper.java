package com.dholubeu.driverservice.web.mapper;

import com.dholubeu.driverservice.domain.Driver;
import com.dholubeu.driverservice.web.dto.DriverDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DriverMapper {

    DriverDto toDto(Driver driver);

    List<DriverDto> toDto(List<Driver> drivers);

    Driver toEntity(DriverDto driverDto);

    List<Driver> toEntity(List<DriverDto> driversDto);

}