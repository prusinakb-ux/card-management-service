package com.github.kprusina.cardmanagement.configuration;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class KafkaConsumersConfig {
  @Value(value = "${spring.kafka.consumer.bootstrap-servers}")
  private String bootstrapServers;

  @Value(value = "${spring.kafka.consumer.group-id}")
  private String groupId;

  private <K, V> ConsumerFactory<K, V> generateFactory(
      Deserializer<K> keyDeserializer, Deserializer<V> valueDeserializer) {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
    return new DefaultKafkaConsumerFactory<>(props, keyDeserializer, valueDeserializer, false);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, String>
      kafkaListenerContainerFactoryCardStatusUpdate() {
    ConcurrentKafkaListenerContainerFactory<String, String> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(generateFactory(new StringDeserializer(), new StringDeserializer()));
    return factory;
  }
}
