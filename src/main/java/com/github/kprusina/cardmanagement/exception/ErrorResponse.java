package com.github.kprusina.cardmanagement.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
  private String code;
  private String id;
  private String description;

  public ErrorResponse(String description) {
    this.description = description;
  }
}
