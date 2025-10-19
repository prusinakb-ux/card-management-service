package com.github.kprusina.cardmanagement.feature.client.kafka;

import com.github.kprusina.cardmanagement.feature.client.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardStatusConsumer {

  private final ClientService clientService;

  @KafkaListener(topics = "${kafka.consumer.topic.card-status-update}")
  public void listen(ConsumerRecord<String, String> record) {
    clientService.updateCardStatus(record.key(), record.value());
  }
}
