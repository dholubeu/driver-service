package com.dholubeu.driverservice.web.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.stream.Stream;

public class MultipartFileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

    private static final long MAX_SIZE = 100000L;

    private static final Stream<String> extensions = Stream.of("application/pdf", "application/msword");

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        return (value != null && isValidSize(value) && isValidExtension(value));
    }

    private boolean isValidSize(MultipartFile file) {
        long currentSize = file.getSize();
        if (currentSize < MAX_SIZE) {
            return true;
        }
        return false;
    }

    private boolean isValidExtension(MultipartFile file) {
        return extensions.anyMatch(e -> Objects.equals(e, file.getContentType()));
    }

}