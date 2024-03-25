package com.dholubeu.driverservice.web.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class AgeValidator implements ConstraintValidator<ValidAge, LocalDate> {

    public static final Integer MIN_AGE = 21;

    @Override
    public boolean isValid(LocalDate dateOfBirth,
                           ConstraintValidatorContext constraintValidatorContext) {
        return dateOfBirth != null && isValidAge(dateOfBirth);
    }

    private boolean isValidAge(LocalDate dateOfBirth) {
        LocalDate currentDate = LocalDate.now();
        LocalDate minDate = currentDate.minusYears(MIN_AGE);
        return !dateOfBirth.isAfter(minDate);
    }

}