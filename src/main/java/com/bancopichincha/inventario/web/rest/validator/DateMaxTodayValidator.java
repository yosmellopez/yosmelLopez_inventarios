package com.bancopichincha.inventario.web.rest.validator;

import com.bancopichincha.inventario.web.rest.validator.annotation.DateMaxToday;
import java.time.LocalDate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateMaxTodayValidator implements ConstraintValidator<DateMaxToday, LocalDate> {

    @Override
    public void initialize(DateMaxToday constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext context) {
        LocalDate now = LocalDate.now();
        return now.isEqual(localDate) || now.isAfter(localDate);
    }
}
