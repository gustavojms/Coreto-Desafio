package br.com.coreto.application.validation;

import br.com.coreto.application.dto.request.OportunidadeRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateOrderValidator implements ConstraintValidator<ValidDateOrder, OportunidadeRequest> {

    @Override
    public boolean isValid(OportunidadeRequest request, ConstraintValidatorContext context) {
        if (request == null) {
            return true;
        }

        boolean valid = true;

        if (request.getDataAbertura() != null && request.getDataLimiteInscricao() != null) {
            if (request.getDataLimiteInscricao().isBefore(request.getDataAbertura())) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                                "Data limite de inscricao deve ser posterior ou igual a data de abertura")
                        .addPropertyNode("dataLimiteInscricao")
                        .addConstraintViolation();
                valid = false;
            }
        }

        return valid;
    }
}
