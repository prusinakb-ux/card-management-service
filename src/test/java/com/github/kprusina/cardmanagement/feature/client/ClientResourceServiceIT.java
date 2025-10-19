package com.github.kprusina.cardmanagement.feature.client;

import static org.junit.jupiter.api.Assertions.*;

import com.github.kprusina.cardmanagement.enumeration.CardStatus;
import com.github.kprusina.cardmanagement.exception.SoftException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ClientResourceServiceIT {

  @Autowired private ClientResourceService clientResourceService;
  @Autowired private ClientRepository clientRepository;

  @Test
  void persistsAndRetrievesClientByOib() {
    Client client =
        Client.builder()
            .firstName("Ivo")
            .lastName("Ivić")
            .oib("12345678903")
            .status(CardStatus.PENDING)
            .build();

    Client saved = clientResourceService.save(client);

    assertNotNull(saved.getId());
    assertTrue(clientResourceService.findByOptionalOib("12345678903").isPresent());
  }

  @Test
  void deletesExistingClientByOib() {
    Client client =
        Client.builder()
            .firstName("Ana")
            .lastName("Anić")
            .oib("98765432103")
            .status(CardStatus.PENDING)
            .build();
    clientRepository.save(client);

    clientResourceService.deleteByOib("98765432103");

    assertFalse(clientRepository.findByOib("98765432103").isPresent());
  }

  @Test
  void throwsSoftExceptionWhenDeletingNonExistingClient() {
    SoftException ex =
        assertThrows(SoftException.class, () -> clientResourceService.deleteByOib("00000000000"));
    assertEquals("client.notFound", ex.getMessageKey());
  }
}
