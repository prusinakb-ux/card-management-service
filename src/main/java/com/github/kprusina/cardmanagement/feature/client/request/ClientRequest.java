package com.github.kprusina.cardmanagement.feature.client.request;

import com.github.kprusina.cardmanagement.enumeration.CardStatus;
import com.github.kprusina.cardmanagement.validator.oib.Oib;
import com.github.kprusina.cardmanagement.validator.value_of_enum.ValueOfEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClientRequest {

  @NotBlank
  @Size(max = 50, message = "First name must be at most 50 characters long")
  @Schema(description = "Client's first name", example = "Ivan")
  private String firstName;

  @Size(max = 50, message = "Last name must be at most 50 characters long")
  @Schema(description = "Client's last name", example = "Horvat")
  @NotBlank
  private String lastName;

  @Oib
  @Size(min = 11, max = 11, message = "OIB must be exactly 11 digits")
  @Schema(description = "Croatian OIB (11 digits)", example = "04882405967")
  private String oib;

  @NotNull
  @ValueOfEnum(enumClass = CardStatus.class)
  @Schema(description = "Card status", example = "PENDING", implementation = CardStatus.class)
  private String status;
}
