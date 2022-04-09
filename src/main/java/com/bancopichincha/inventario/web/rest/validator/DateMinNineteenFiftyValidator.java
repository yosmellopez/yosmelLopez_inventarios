package com.bancopichincha.inventario.web.rest.validator;

import com.bancopichincha.inventario.web.rest.validator.annotation.DateMaxToday;
import com.bancopichincha.inventario.web.rest.validator.annotation.DateMinNineteenFifty;
import java.time.LocalDate;
import java.time.Month;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.context.annotation.Profile;

public class DateMinNineteenFiftyValidator implements ConstraintValidator<DateMinNineteenFifty, LocalDate> {

    @Override
    public void initialize(DateMinNineteenFifty constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext context) {
        LocalDate nineTeenFifty = LocalDate.of(1950, Month.JANUARY, 1);
        return nineTeenFifty.isEqual(localDate) || nineTeenFifty.isBefore(localDate);
    }
}
