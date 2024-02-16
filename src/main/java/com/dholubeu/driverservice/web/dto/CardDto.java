package com.dholubeu.driverservice.web.dto;

import com.dholubeu.driverservice.web.dto.validation.OnCreate;
import com.dholubeu.driverservice.web.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
public class CardDto {

    @NotNull(groups = OnUpdate.class)
    private Long id;

    @Pattern(regexp = "\\d{16}", message = "Invalid bank card number",
            groups = {OnCreate.class, OnUpdate.class})
    @NotEmpty(message = "Number is required", groups = {OnCreate.class, OnUpdate.class})
    private String number;

    @Future(message = "Invalid bank validity", groups = {OnCreate.class, OnUpdate.class})
    private LocalDate validity;

    @Pattern(regexp = "\\d{3}", message = "Invalid CVV",
            groups = {OnCreate.class, OnUpdate.class})
    @NotEmpty(message = "CVV is required", groups = {OnCreate.class, OnUpdate.class})
    private String cvv;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal balance;

}