package com.github.kprusina.cardmanagement.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolationException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private final ObjectMapper objectMapper;
  private final I18nMessageResolver i18nMessageResolver;

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {

    Map<String, Set<String>> errors =
        ex.getBindingResult().getFieldErrors().stream()
            .collect(
                Collectors.groupingBy(
                    FieldError::getField,
                    Collectors.mapping(FieldError::getDefaultMessage, Collectors.toSet())));

    Map<String, Set<String>> globalErrors =
        ex.getBindingResult().getGlobalErrors().stream()
            .collect(
                Collectors.groupingBy(
                    ObjectError::getObjectName,
                    Collectors.mapping(ObjectError::getDefaultMessage, Collectors.toSet())));

    errors.putAll(globalErrors);
    log.error("Validation failed: {}", errors);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  @ExceptionHandler(SoftException.class)
  protected ResponseEntity<ErrorResponse> handleSoftException(SoftException ex) {
    String message = i18nMessageResolver.translate(ex.getMessageKey(), ex.getArgs());
    log.error(message, ex);
    return new ResponseEntity<>(ex.toErrorResponse(message), ex.getStatus());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
    Map<String, Set<String>> errors =
        ex.getConstraintViolations().stream()
            .collect(
                Collectors.groupingBy(
                    cv -> cv.getPropertyPath().toString(),
                    Collectors.mapping(cv -> cv.getMessage(), Collectors.toSet())));
    log.error("Constraint violations: {}", errors);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  @ExceptionHandler(CardApiException.class)
  public ResponseEntity<ErrorResponse> handleCardApiException(CardApiException ex) {
    log.error("Card API error: {}", ex.getError().getDescription());
    return ResponseEntity.status(ex.getStatus()).body(ex.getError());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
    log.error("Unexpected exception", ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new ErrorResponse(null, null, "Unexpected error occurred. Please try again later."));
  }
}
