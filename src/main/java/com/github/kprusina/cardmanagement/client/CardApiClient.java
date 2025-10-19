package com.github.kprusina.cardmanagement.client;

import com.github.kprusina.cardmanagement.client.response.CardApiResponse;
import com.github.kprusina.cardmanagement.feature.client.request.ClientRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "cardApiClient", url = "${card.api.url}")
public interface CardApiClient {

  @PostMapping("/api/v1/card-request")
  ResponseEntity<CardApiResponse> createCard(@RequestBody ClientRequest request);
}
