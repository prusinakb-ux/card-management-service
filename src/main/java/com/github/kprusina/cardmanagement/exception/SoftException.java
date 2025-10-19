package com.github.kprusina.cardmanagement.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

@Getter
public class SoftException extends I18nException {

  public static final HttpStatus DEFAULT_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;
  private final HttpStatus status;
  private final String code;
  private final String id;

  public SoftException(HttpStatus status, String code, String id, String messageKey, @Nullable Object... args) {
    super(messageKey, args);
    this.status = status;
    this.code = code;
    this.id = id;
  }

  public SoftException(String code, String id, String messageKey, @Nullable Object... args) {
    this(DEFAULT_STATUS, code, id, messageKey, args);
  }

  public ErrorResponse toErrorResponse(String translatedMessage) {
    return new ErrorResponse(code, id, translatedMessage);
  }
}