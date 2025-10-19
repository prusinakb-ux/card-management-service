package com.github.kprusina.cardmanagement.validator;

import static org.junit.jupiter.api.Assertions.*;

import com.github.kprusina.cardmanagement.validator.oib.OibValidator;
import org.junit.jupiter.api.Test;

class OibValidatorTest {

  private final OibValidator validator = new OibValidator();

  @Test
  void returnsTrueForNullValue() {
    assertTrue(validator.isValid(null, null));
  }

  @Test
  void returnsFalseForInvalidLength() {
    assertFalse(validator.isValid("123", null));
  }

  @Test
  void returnsFalseForInvalidChecksum() {
    assertFalse(validator.isValid("12345678901", null));
  }

  @Test
  void returnsTrueForValidOib() {
    assertTrue(validator.isValid("12345678903", null));
  }
}
