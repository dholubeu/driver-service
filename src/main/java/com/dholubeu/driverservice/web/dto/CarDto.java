package com.dholubeu.driverservice.web.dto;

import com.dholubeu.driverservice.web.dto.validation.OnCreate;
import com.dholubeu.driverservice.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@RequiredArgsConstructor
public class CarDto {

    @NotNull(groups = OnUpdate.class)
    private Long id;

    @Length(min = 3, max = 30, message = "Mark must be between 3 and 30 characters",
            groups = {OnCreate.class, OnUpdate.class})
    @NotEmpty(message = "Mark is required", groups = {OnCreate.class, OnUpdate.class})
    private String mark;

    @Length(min = 3, max = 30, message = "Model must be between 3 and 30 characters",
            groups = {OnCreate.class, OnUpdate.class})
    @NotEmpty(message = "Model is required", groups = {OnCreate.class, OnUpdate.class})
    private String model;

    @Pattern(regexp = "\\d{4}[A-Za-z]{2}-\\d", message = "Invalid number")
    @NotEmpty(message = "Number is required", groups = {OnCreate.class, OnUpdate.class})
    private String number;

    @NotEmpty(message = "Address is required", groups = {OnCreate.class, OnUpdate.class})
    private String currentAddress;

}