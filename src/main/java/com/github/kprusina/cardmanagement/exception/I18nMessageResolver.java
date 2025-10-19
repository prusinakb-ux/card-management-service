package com.github.kprusina.cardmanagement.exception;

import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class I18nMessageResolver {

  private final MessageSource messageSource;

  public String translate(String messageKey, @Nullable Object... args) {
    Locale current = LocaleContextHolder.getLocale();
    log.info("Current locale = {}", current);
    return messageSource.getMessage(messageKey, args, messageKey, current);
  }
}
