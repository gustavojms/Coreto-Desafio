package br.com.coreto.validation;

import br.com.coreto.application.validation.CNPJValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CNPJValidatorTest {

    private CNPJValidator validator;

    @BeforeEach
    void setUp() {
        validator = new CNPJValidator();
    }

    @Test
    void shouldAcceptValidCnpj() {
        assertTrue(validator.isValid("11.222.333/0001-81", null));
    }

    @Test
    void shouldAcceptValidCnpjWithoutMask() {
        assertTrue(validator.isValid("11222333000181", null));
    }

    @Test
    void shouldRejectInvalidCnpj() {
        assertFalse(validator.isValid("11222333000182", null));
    }

    @Test
    void shouldRejectAllSameDigits() {
        assertFalse(validator.isValid("11111111111111", null));
    }

    @Test
    void shouldRejectWrongLength() {
        assertFalse(validator.isValid("1234567", null));
    }

    @Test
    void shouldAcceptNull() {
        assertTrue(validator.isValid(null, null));
    }

    @Test
    void shouldAcceptEmpty() {
        assertTrue(validator.isValid("", null));
    }
}
