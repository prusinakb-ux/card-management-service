package com.github.kprusina.cardmanagement.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kprusina.cardmanagement.exception.CardApiException;
import com.github.kprusina.cardmanagement.exception.ErrorResponse;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class CardApiErrorDecoder implements ErrorDecoder {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public Exception decode(String methodKey, Response response) {
    if (response.body() == null) {
      return createFallback(response);
    }

    try {
      ErrorResponse error = objectMapper.readValue(response.body().asInputStream(), ErrorResponse.class);
      fillDefaults(error);
      return new CardApiException(HttpStatus.valueOf(response.status()), error);
    } catch (Exception e) {
      log.error("Failed to parse Card API error response", e);
      return createFallback(response);
    }
  }

  private void fillDefaults(ErrorResponse error) {
    if (error.getId() == null) error.setId(UUID.randomUUID().toString());
    if (error.getCode() == null) error.setCode("CARD_API_ERROR");
    if (error.getDescription() == null) error.setDescription("Unknown error from Card API");
  }

  private CardApiException createFallback(Response response) {
    return new CardApiException(
            HttpStatus.valueOf(response.status()),
            new ErrorResponse("CARD_API_ERROR", UUID.randomUUID().toString(), "Unknown error from Card API")
    );
  }
}
