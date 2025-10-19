package com.github.kprusina.cardmanagement.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class I18nMessageResolver {

  private final MessageSource messageSource;

  public String translate(String messageKey, @Nullable Object... args) {
    return messageSource.getMessage(messageKey, args, LocaleContextHolder.getLocale());
  }
}
