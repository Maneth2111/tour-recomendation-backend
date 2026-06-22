package com.example.Tour_Recommendation.dto.request;

import com.example.Tour_Recommendation.model.Enum.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequest {

    @Size(max = 100)
    private String fullName;

    @Email
    private String email;

    @Size(max = 20)
    private String phone;

    private String avatarUrl;

    private Role role;
}
