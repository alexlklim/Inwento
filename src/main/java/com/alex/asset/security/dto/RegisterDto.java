package com.alex.asset.security.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data @Builder
@AllArgsConstructor @NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "Register DTO")
public class RegisterDto {

  @Schema(description = "First name", example = "Alex")
  private String firstName;

  @Schema(description = "Last name", example = "Klim")
  private String lastName;

  @Schema(description = "Phone", example = "+48 877 202 134")
  private String phone;

  @Schema(description = "Email", example = "alex@gmail.com")
  private String email;

  @Schema(description = "Password", example = "1122")
  private String password;

  @Schema(description = "Role", example = "ADMIN")
  private String role;
}


