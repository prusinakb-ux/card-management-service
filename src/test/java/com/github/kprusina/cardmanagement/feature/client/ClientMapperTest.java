package com.github.kprusina.cardmanagement.feature.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.kprusina.cardmanagement.enumeration.CardStatus;
import com.github.kprusina.cardmanagement.feature.client.mapper.ClientMapper;
import com.github.kprusina.cardmanagement.feature.client.request.ClientRequest;
import com.github.kprusina.cardmanagement.feature.client.response.ClientResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ClientMapperTest {

  @Autowired private ClientMapper mapper;

  @Test
  void toEntity_ShouldMapRequestToEntity() {
    ClientRequest request = new ClientRequest("John", "Doe", "12345678901", "PENDING");

    Client entity = mapper.toEntity(request);

    assertEquals("John", entity.getFirstName());
    assertEquals("Doe", entity.getLastName());
    assertEquals("12345678901", entity.getOib());
    assertEquals(CardStatus.PENDING, entity.getStatus());
  }

  @Test
  void toResponse_ShouldMapEntityToResponse() {
    Client client = new Client();
    client.setFirstName("John");
    client.setLastName("Doe");
    client.setOib("12345678901");
    client.setStatus(CardStatus.PENDING);

    ClientResponse response = mapper.toResponse(client);

    assertEquals("John", response.getFirstName());
    assertEquals("Doe", response.getLastName());
    assertEquals("12345678901", response.getOib());
    assertEquals("PENDING", response.getStatus());
  }
}
