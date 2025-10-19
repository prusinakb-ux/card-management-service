package com.github.kprusina.cardmanagement.feature.client;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.github.kprusina.cardmanagement.exception.SoftException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

class ClientResourceServiceTest {

  @Mock private ClientRepository clientRepository;
  @InjectMocks private ClientResourceService clientResourceService;

  private Client client;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    client = Client.builder().oib("12345678901").build();
  }

  @Test
  void throwsConflictWhenSavingDuplicateOib() {
    when(clientRepository.save(client)).thenThrow(DataIntegrityViolationException.class);

    SoftException ex = assertThrows(SoftException.class, () -> clientResourceService.save(client));

    assertEquals(HttpStatus.CONFLICT, ex.getStatus());
    assertEquals("client.duplicateOib", ex.getMessageKey());
  }

  @Test
  void throwsNotFoundWhenDeletingNonExistingClient() {
    when(clientRepository.deleteByOib("12345678901")).thenReturn(0);

    SoftException ex =
        assertThrows(SoftException.class, () -> clientResourceService.deleteByOib("12345678901"));

    assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    assertEquals("client.notFound", ex.getMessageKey());
  }
}
