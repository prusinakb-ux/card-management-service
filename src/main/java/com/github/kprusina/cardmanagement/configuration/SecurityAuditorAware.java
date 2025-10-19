package com.github.kprusina.cardmanagement.configuration;

import io.micrometer.common.lang.NonNull;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component("auditorProvider")
public class SecurityAuditorAware implements AuditorAware<String> {

  //  @Override
  //  @NonNull
  //  public Optional<String> getCurrentAuditor() {
  //
  //    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
  //
  //    if (authentication == null || !authentication.isAuthenticated()) {
  //      return Optional.of("system");
  //    }
  //
  //    return Optional.of(authentication.getName());
  // }

  @Override
  @NonNull
  public Optional<String> getCurrentAuditor() {
    return Optional.of("system");
  }
}
