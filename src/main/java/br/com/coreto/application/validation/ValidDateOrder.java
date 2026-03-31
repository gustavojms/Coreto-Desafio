package br.com.coreto.application.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateOrderValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateOrder {
    String message() default "Data limite de inscricao deve ser posterior a data de abertura";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
