package com.github.kprusina.cardmanagement.validator.oib;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OibValidator implements ConstraintValidator<Oib, String> {
  @Override
  public boolean isValid(String input, ConstraintValidatorContext context) {
    if (input == null) return true;
    if (input.length() != 11) return false;
    String checkDigit = input.substring(input.length() - 1);
    String data = input.substring(0, input.length() - 1);
    return checkDigit.equals(calculateChecksum(data));
  }

  public static String calculateChecksum(String input) {
    int lastRowResult = 10;

    for (int i = 0; i < input.length(); i++) {
      int number = Integer.parseInt(String.valueOf(input.charAt(i)));
      number = (number + lastRowResult) % 10;
      number = (number == 0 ? 10 : number) * 2;
      lastRowResult = number % 11;
    }
    return String.valueOf(checksum(lastRowResult));
  }

  public static int checksum(int number) {
    return number == 1 ? 0 : 11 - number;
  }
}
