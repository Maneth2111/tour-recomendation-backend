package com.example.Tour_Recommendation.service;

import com.example.Tour_Recommendation.common.ApiResponse;
import com.example.Tour_Recommendation.dto.LoginRequest;
import com.example.Tour_Recommendation.dto.RegisterRequest;
import com.example.Tour_Recommendation.dto.response.LoginResponse;
import com.example.Tour_Recommendation.dto.response.UserResponse;
import com.example.Tour_Recommendation.security.CustomUserDetails;

public interface AuthService {

    ApiResponse<UserResponse> register(RegisterRequest request);

    ApiResponse<LoginResponse> login(LoginRequest request);

    ApiResponse<UserResponse> getMe(CustomUserDetails userDetails);
}
