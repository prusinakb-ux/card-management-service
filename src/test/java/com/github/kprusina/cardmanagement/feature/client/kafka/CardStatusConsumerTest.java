package com.github.kprusina.cardmanagement.feature.client.kafka;

import static org.mockito.Mockito.*;

import com.github.kprusina.cardmanagement.feature.client.ClientService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CardStatusConsumerTest {

  @Mock private ClientService clientService;
  @InjectMocks private CardStatusConsumer consumer;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void delegatesCardStatusUpdateToClientService() {
    ConsumerRecord<String, String> record =
        new ConsumerRecord<>("topic", 0, 0L, "12345678901", "APPROVED");

    consumer.listen(record);

    verify(clientService).updateCardStatus("12345678901", "APPROVED");
  }
}
