package com.example.Tour_Recommendation.dto.response.auth;

import com.example.Tour_Recommendation.dto.response.user.UserResponse;
import com.example.Tour_Recommendation.model.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private Long id;
    private String name;
    private String email;
    private String role;

    @JsonProperty("created_at")
    private Instant createdAt;

    public static LoginResponse from(User user, String token) {
        UserResponse userResponse = UserResponse.from(user);
        return LoginResponse.builder()
                .token(token)
                .id(userResponse.getId())
                .name(userResponse.getName())
                .email(userResponse.getEmail())
                .role(userResponse.getRole())
                .createdAt(userResponse.getCreatedAt())
                .build();
    }
}
