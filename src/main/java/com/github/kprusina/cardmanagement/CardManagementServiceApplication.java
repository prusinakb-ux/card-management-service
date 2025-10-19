package com.github.kprusina.cardmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.github.kprusina.cardmanagement.client")
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class CardManagementServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(CardManagementServiceApplication.class, args);
  }
}
