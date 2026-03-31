package br.com.coreto.application.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CNPJValidator implements ConstraintValidator<CNPJ, String> {

    @Override
    public boolean isValid(String cnpj, ConstraintValidatorContext context) {
        if (cnpj == null || cnpj.isBlank()) {
            return true; // @NotBlank handles nulls
        }

        String digits = cnpj.replaceAll("[^0-9]", "");

        if (digits.length() != 14) {
            return false;
        }

        // Reject all-same-digit CNPJs
        if (digits.chars().distinct().count() == 1) {
            return false;
        }

        // First check digit
        int[] weights1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            sum += Character.getNumericValue(digits.charAt(i)) * weights1[i];
        }
        int remainder = sum % 11;
        int firstDigit = remainder < 2 ? 0 : 11 - remainder;

        if (Character.getNumericValue(digits.charAt(12)) != firstDigit) {
            return false;
        }

        // Second check digit
        int[] weights2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        sum = 0;
        for (int i = 0; i < 13; i++) {
            sum += Character.getNumericValue(digits.charAt(i)) * weights2[i];
        }
        remainder = sum % 11;
        int secondDigit = remainder < 2 ? 0 : 11 - remainder;

        return Character.getNumericValue(digits.charAt(13)) == secondDigit;
    }
}
