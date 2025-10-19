package com.github.kprusina.cardmanagement.feature.client.response;

import lombok.*;

@Data
@Builder
public class ClientResponse {
  private Long id;
  private String firstName;
  private String lastName;
  private String oib;
  private String status;
}
