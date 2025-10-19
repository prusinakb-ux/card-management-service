package com.github.kprusina.cardmanagement.configuration;

import com.github.kprusina.cardmanagement.client.CardApiErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CardApiClientConfig {
  @Bean
  public ErrorDecoder cardApiErrorDecoder() {
    return new CardApiErrorDecoder();
  }
}
