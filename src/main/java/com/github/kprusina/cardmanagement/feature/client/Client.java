package com.github.kprusina.cardmanagement.feature.client;

import com.github.kprusina.cardmanagement.creation_audit.CreationAudit;
import com.github.kprusina.cardmanagement.enumeration.CardStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client extends CreationAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String firstName;
  private String lastName;

  @Column(unique = true, nullable = false, length = 11)
  private String oib;

  @Enumerated(EnumType.STRING)
  private CardStatus status;
}
