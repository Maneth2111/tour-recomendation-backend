package com.example.Tour_Recommendation.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateProfileRequest {

    @Size(max = 100)
    @JsonAlias("full_name")
    @Schema(example = "maneth", description = "Full name")
    private String name;

    @Size(max = 20)
    @Schema(example = "012345678", description = "Phone number")
    private String phone;

    @JsonProperty("avatar_url")
    @Schema(example = "http://localhost:8081/uploads/avatars/example.jpg", description = "Avatar image URL")
    private String avatarUrl;
}