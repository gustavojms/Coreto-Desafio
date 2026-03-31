package br.com.coreto.application.validation;

import br.com.coreto.application.dto.request.OportunidadeRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ParceriaValidator implements ConstraintValidator<ValidParceria, OportunidadeRequest> {

    @Override
    public boolean isValid(OportunidadeRequest request, ConstraintValidatorContext context) {
        if (request == null) {
            return true;
        }

        if (Boolean.TRUE.equals(request.getPossuiParceria())) {
            if (request.getParceiros() == null || request.getParceiros().isEmpty()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                                "Parceiros sao obrigatorios quando possui parceria e verdadeiro")
                        .addPropertyNode("parceiros")
                        .addConstraintViolation();
                return false;
            }
        }

        return true;
    }
}
