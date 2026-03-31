package br.com.coreto.application.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = VinculoValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidVinculo {
    String message() default "Detalhes do vinculo sao obrigatorios quando possui vinculo";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
