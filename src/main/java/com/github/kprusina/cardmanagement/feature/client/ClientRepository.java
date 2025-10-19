package com.github.kprusina.cardmanagement.feature.client;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface ClientRepository
    extends JpaRepository<Client, Long>, QuerydslPredicateExecutor<Client> {
  Optional<Client> findByOib(String oib);

  @Modifying
  @Query("DELETE FROM Client c WHERE c.oib = :oib")
  int deleteByOib(@Param("oib") String oib);
}
