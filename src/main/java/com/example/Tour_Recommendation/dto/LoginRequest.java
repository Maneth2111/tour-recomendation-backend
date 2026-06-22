package com.example.Tour_Recommendation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank
    @Email
    @Schema(example = "maneth@gmail.com")
    private String email;

    @NotBlank
    @Schema(example = "12345678")
    private String password;
}
