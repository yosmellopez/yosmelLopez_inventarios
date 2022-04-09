package com.bancopichincha.inventario.web.rest.validator.annotation;

import com.bancopichincha.inventario.web.rest.validator.DateMaxTodayValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateMaxTodayValidator.class)
public @interface DateMaxToday {

    String message() default "La fecha máxima de búsqueda no deber ser mayor que la fecha de hoy";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
