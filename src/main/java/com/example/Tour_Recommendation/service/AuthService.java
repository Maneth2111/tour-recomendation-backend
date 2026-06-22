package com.example.Tour_Recommendation.service;

import com.example.Tour_Recommendation.common.ApiResponse;
import com.example.Tour_Recommendation.dto.request.GoogleAuthRequest;
import com.example.Tour_Recommendation.dto.LoginRequest;
import com.example.Tour_Recommendation.dto.RegisterRequest;
import com.example.Tour_Recommendation.dto.response.auth.LoginResponse;
import com.example.Tour_Recommendation.dto.response.user.UserResponse;
import com.example.Tour_Recommendation.security.CustomUserDetails;

public interface AuthService {

    ApiResponse<UserResponse> register(RegisterRequest request);

    ApiResponse<LoginResponse> login(LoginRequest request);

    ApiResponse<LoginResponse> googleAuth(GoogleAuthRequest request);

    ApiResponse<UserResponse> getMe(CustomUserDetails userDetails);
}
