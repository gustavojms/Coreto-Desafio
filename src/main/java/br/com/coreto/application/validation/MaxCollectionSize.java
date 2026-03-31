package br.com.coreto.application.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MaxCollectionSizeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxCollectionSize {
    String message() default "Colecao excede o tamanho maximo";
    int value();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
