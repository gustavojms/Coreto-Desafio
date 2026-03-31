package br.com.coreto.application.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CNPJValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CNPJ {
    String message() default "CNPJ invalido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
