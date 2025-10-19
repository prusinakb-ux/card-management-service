package com.github.kprusina.cardmanagement.creation_audit;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class CreationAudit {

  @CreatedBy
  @Column(name = "user_created", updatable = false)
  private String userCreated;

  @LastModifiedBy
  @Column(name = "user_modified")
  private String userModified;

  @CreatedDate
  @Column(name = "date_created", nullable = false, updatable = false)
  private LocalDateTime dateCreated;

  @LastModifiedDate
  @Column(name = "date_modified")
  private LocalDateTime dateModified;
}
