package com.bancopichincha.inventario.web.rest.validator.annotation;

import com.bancopichincha.inventario.web.rest.validator.DateMaxTodayValidator;
import com.bancopichincha.inventario.web.rest.validator.DateMinNineteenFiftyValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateMinNineteenFiftyValidator.class)
public @interface DateMinNineteenFifty {

    String message() default "La fecha mínima de búsqueda no deber ser menor que 1950";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
