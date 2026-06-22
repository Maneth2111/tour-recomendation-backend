package com.example.Tour_Recommendation.service;

import com.example.Tour_Recommendation.common.ApiResponse;
import com.example.Tour_Recommendation.dto.request.UpdateUserRequest;
import com.example.Tour_Recommendation.dto.request.UpdateUserStatusRequest;
import com.example.Tour_Recommendation.dto.response.user.UserResponse;

import java.util.List;

public interface UserService {

    ApiResponse<List<UserResponse>> getAllUsers();

    ApiResponse<UserResponse> getUserById(Long id);

    ApiResponse<UserResponse> updateUser(Long id, UpdateUserRequest request);

    ApiResponse<UserResponse> updateUserStatus(Long id, UpdateUserStatusRequest request);
}
