package br.com.coreto.application.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ParceriaValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidParceria {
    String message() default "Parceiros sao obrigatorios quando possui parceria";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
