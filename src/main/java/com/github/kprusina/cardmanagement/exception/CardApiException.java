package com.github.kprusina.cardmanagement.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CardApiException extends RuntimeException {
  private final HttpStatus status;
  private final ErrorResponse error;

  public CardApiException(HttpStatus status, ErrorResponse error) {
    super(error.getDescription());
    this.status = status;
    this.error = error;
  }
}
