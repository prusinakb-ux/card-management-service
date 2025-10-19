package com.github.kprusina.cardmanagement.feature.client;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.github.kprusina.cardmanagement.client.CardApiClient;
import com.github.kprusina.cardmanagement.enumeration.CardStatus;
import com.github.kprusina.cardmanagement.exception.SoftException;
import com.github.kprusina.cardmanagement.feature.client.mapper.ClientMapper;
import com.github.kprusina.cardmanagement.feature.client.request.ClientRequest;
import com.github.kprusina.cardmanagement.feature.client.response.ClientResponse;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ClientServiceTest {

  @Mock private ClientResourceService clientResourceService;
  @Mock private ClientMapper mapper;
  @Mock private CardApiClient cardApiClient;
  @InjectMocks private ClientService clientService;

  private ClientRequest request;
  private Client client;
  private ClientResponse response;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    request = new ClientRequest("John", "Doe", "12345678901", "PENDING");
    client =
        Client.builder()
            .firstName("John")
            .lastName("Doe")
            .oib("12345678901")
            .status(CardStatus.PENDING)
            .build();
    response =
        ClientResponse.builder()
            .firstName("John")
            .lastName("Doe")
            .oib("12345678901")
            .status("PENDING")
            .build();
  }

  @Test
  void createsClientAndReturnsMappedResponse() {
    when(mapper.toEntity(request)).thenReturn(client);
    when(clientResourceService.save(client)).thenReturn(client);
    when(mapper.toResponse(client)).thenReturn(response);

    ClientResponse result = clientService.createClient(request);

    assertEquals("John", result.getFirstName());
    verify(clientResourceService).save(client);
    verify(mapper).toResponse(client);
  }

  @Test
  void propagatesSoftExceptionWhenCreateFails() {
    when(mapper.toEntity(request)).thenReturn(client);
    when(clientResourceService.save(client))
        .thenThrow(
            new SoftException(
                HttpStatus.CONFLICT, "DUPLICATE", "12345678901", "client.duplicateOib"));

    assertThrows(SoftException.class, () -> clientService.createClient(request));
  }

  @Test
  void returnsOkAndTriggersCardApiWhenClientExists() {
    when(clientResourceService.findByOptionalOib("12345678901")).thenReturn(Optional.of(client));
    when(mapper.toResponse(client)).thenReturn(response);
    when(mapper.toRequest(client)).thenReturn(request);

    ResponseEntity<ClientResponse> result = clientService.getClientByOib("12345678901");

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals("John", Objects.requireNonNull(result.getBody()).getFirstName());
    verify(cardApiClient).createCard(request);
  }

  @Test
  void returnsNoContentWhenClientDoesNotExist() {
    when(clientResourceService.findByOptionalOib("12345678901")).thenReturn(Optional.empty());

    ResponseEntity<ClientResponse> result = clientService.getClientByOib("12345678901");

    assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    assertNull(result.getBody());
    verify(cardApiClient, never()).createCard(any());
  }

  @Test
  void updatesCardStatusWhenValidAndDifferent() {
    when(clientResourceService.findByOptionalOib("12345678901")).thenReturn(Optional.of(client));

    clientService.updateCardStatus("12345678901", "APPROVED");

    assertEquals(CardStatus.APPROVED, client.getStatus());
    verify(clientResourceService).save(client);
  }

  @Test
  void doesNothingWhenStatusIsInvalid() {
    when(clientResourceService.findByOptionalOib("12345678901")).thenReturn(Optional.of(client));

    clientService.updateCardStatus("12345678901", "INVALID");

    assertEquals(CardStatus.PENDING, client.getStatus());
    verify(clientResourceService, never()).save(client);
  }

  @Test
  void doesNothingWhenStatusIsSame() {
    client.setStatus(CardStatus.APPROVED);
    when(clientResourceService.findByOptionalOib("12345678901")).thenReturn(Optional.of(client));

    clientService.updateCardStatus("12345678901", "APPROVED");

    verify(clientResourceService, never()).save(client);
  }

  @Test
  void doesNothingWhenClientDoesNotExist() {
    when(clientResourceService.findByOptionalOib("12345678901")).thenReturn(Optional.empty());

    clientService.updateCardStatus("12345678901", "APPROVED");

    verify(clientResourceService, never()).save(any());
  }

  @Test
  void deletesClientByOib() {
    clientService.deleteClient("12345678901");

    verify(clientResourceService).deleteByOib("12345678901");
  }
}
