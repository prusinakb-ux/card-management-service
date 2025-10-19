package com.github.kprusina.cardmanagement.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;

@SpringBootTest
class I18nMessageResolverTest {

  @Autowired private I18nMessageResolver resolver;

  @Test
  void returnsCroatianMessageWhenLocaleIsHr() {
    LocaleContextHolder.setLocale(Locale.forLanguageTag("hr-HR"));

    String msg = resolver.translate("client.notFound");

    assertEquals("Nije moguće dohvatiti podatke o klijentu.", msg);
  }

  @Test
  void returnsEnglishMessageWhenLocaleIsEn() {
    LocaleContextHolder.setLocale(Locale.ENGLISH);

    String msg = resolver.translate("client.notFound");

    assertEquals("Client data not found.", msg);
  }

  @Test
  void fallsBackToKeyIfTranslationMissing() {
    LocaleContextHolder.setLocale(Locale.ENGLISH);

    String msg = resolver.translate("non.existent.key");

    assertEquals("non.existent.key", msg);
  }
}
