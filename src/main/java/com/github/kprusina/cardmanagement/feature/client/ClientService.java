package com.github.kprusina.cardmanagement.feature.client;

import com.github.kprusina.cardmanagement.client.CardApiClient;
import com.github.kprusina.cardmanagement.enumeration.CardStatus;
import com.github.kprusina.cardmanagement.feature.client.mapper.ClientMapper;
import com.github.kprusina.cardmanagement.feature.client.request.ClientRequest;
import com.github.kprusina.cardmanagement.feature.client.response.ClientResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(propagation = Propagation.NEVER)
public class ClientService {

  private final ClientResourceService clientResourceService;
  private final ClientMapper mapper;
  private final CardApiClient cardApiClient;

  public ClientResponse createClient(ClientRequest request) {
    return mapper.toResponse(clientResourceService.save(mapper.toEntity(request)));
  }

  public ResponseEntity<ClientResponse> getClientByOib(String oib) {
    return clientResourceService
        .findByOptionalOib(oib)
        .map(
            client -> {
              ClientResponse clientResponse = mapper.toResponse(client);
              ClientRequest clientRequest = mapper.toRequest(client);
              cardApiClient.createCard(clientRequest);
              return ResponseEntity.ok(clientResponse);
            })
        .orElseGet(() -> ResponseEntity.noContent().build());
  }

  public void updateCardStatus(String oib, String status) {
    clientResourceService
        .findByOptionalOib(oib)
        .ifPresentOrElse(
            client -> {
              try {
                CardStatus newStatus = CardStatus.valueOf(status.toUpperCase());

                if (client.getStatus() == newStatus) {
                  log.info("Client {} already has card status {}", oib, newStatus);
                  return;
                }

                if (!isValidStatusTransition(client.getStatus(), newStatus)) {
                  log.warn(
                      "Cannot update client card status {} from {} to {}",
                      oib,
                      client.getStatus(),
                      newStatus);
                  return;
                }

                client.setStatus(newStatus);
                clientResourceService.save(client);
                log.info("Updated client card {} status to {}", oib, newStatus);

              } catch (IllegalArgumentException e) {
                log.warn("Invalid card status '{}' for client {}", status, oib);
              }
            },
            () -> log.warn("Client not found for OIB={}", oib));
  }

  private boolean isValidStatusTransition(CardStatus current, CardStatus next) {
    return current == CardStatus.PENDING
        && (next == CardStatus.APPROVED || next == CardStatus.REJECTED);
  }

  public void deleteClient(String oib) {
    clientResourceService.deleteByOib(oib);
  }
}
