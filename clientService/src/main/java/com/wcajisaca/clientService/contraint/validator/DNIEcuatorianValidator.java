package com.wcajisaca.clientService.contraint.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DNIEcuatorianValidator implements ConstraintValidator<EcuatorianDNI, String> {
    @Override
    public boolean isValid(String dni, ConstraintValidatorContext constraintValidatorContext) {
        boolean correctDNI = false;
        if (dni == null) {
            return false;
        }
        if (dni.length() == 10) {
            int thirdDigit = Integer.parseInt(dni.substring(2, 3));
            if (thirdDigit <= 6) {
                int[] coefficientValidDni = {2, 1, 2, 1, 2, 1, 2, 1, 2};
                int verify = Integer.parseInt(dni.substring(9, 10));
                int sum = 0;
                int digit;
                for (int i = 0; i < (dni.length() - 1); i++) {
                    digit = Integer.parseInt(dni.substring(i, i + 1)) * coefficientValidDni[i];
                    sum += ((digit % 10) + (digit / 10));
                }
                if ((sum % 10 == 0) && (0 == verify)) {
                    correctDNI = true;
                } else correctDNI = (10 - (sum % 10)) == verify;
            }
        }
        return correctDNI;
    }
}