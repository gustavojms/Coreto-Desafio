package br.com.coreto.application.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Collection;

public class MaxCollectionSizeValidator implements ConstraintValidator<MaxCollectionSize, Collection<?>> {

    private int maxSize;

    @Override
    public void initialize(MaxCollectionSize constraintAnnotation) {
        this.maxSize = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Collection<?> collection, ConstraintValidatorContext context) {
        if (collection == null) {
            return true;
        }
        return collection.size() <= maxSize;
    }
}
