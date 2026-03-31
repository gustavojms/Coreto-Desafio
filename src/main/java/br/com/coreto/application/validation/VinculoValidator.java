package br.com.coreto.application.validation;

import br.com.coreto.application.dto.request.ResolvedorRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class VinculoValidator implements ConstraintValidator<ValidVinculo, ResolvedorRequest> {

    @Override
    public boolean isValid(ResolvedorRequest request, ConstraintValidatorContext context) {
        if (request == null) {
            return true;
        }

        if (Boolean.TRUE.equals(request.getPossuiVinculo())) {
            if (request.getVinculoDetalhes() == null || request.getVinculoDetalhes().isBlank()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                                "Detalhes do vinculo sao obrigatorios quando possui vinculo e verdadeiro")
                        .addPropertyNode("vinculoDetalhes")
                        .addConstraintViolation();
                return false;
            }
        }

        return true;
    }
}
