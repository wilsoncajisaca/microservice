package com.wcajisaca.clientService.contraint.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.stream.IntStream;

public class DNIEcuatorianValidator implements ConstraintValidator<EcuatorianDNI, String> {
    @Override
    public boolean isValid(String dni, ConstraintValidatorContext constraintValidatorContext) {
        if (dni == null || dni.length() != 10) {
            return false;
        }

        int thirdDigit = Character.getNumericValue(dni.charAt(2));
        if (thirdDigit > 6) {
            return false;
        }

        int[] coefficientValidDni = {2, 1, 2, 1, 2, 1, 2, 1, 2};
        int verifyDigit = Character.getNumericValue(dni.charAt(9));

        int sum = IntStream.range(0, dni.length() - 1)
                .map(i -> Character.getNumericValue(dni.charAt(i)) * coefficientValidDni[i])
                .map(digit -> (digit % 10) + (digit / 10))
                .sum();

        return (sum % 10 == 0 && verifyDigit == 0) || (10 - (sum % 10)) == verifyDigit;
    }
}