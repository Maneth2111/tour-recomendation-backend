package com.example.Tour_Recommendation.service.serviceImp;

import com.example.Tour_Recommendation.common.ApiResponse;
import com.example.Tour_Recommendation.dto.request.UpdateProfileRequest;
import com.example.Tour_Recommendation.dto.request.UpdateUserRequest;
import com.example.Tour_Recommendation.dto.request.UpdateUserStatusRequest;
import com.example.Tour_Recommendation.dto.response.user.UserResponse;
import com.example.Tour_Recommendation.exception.UserNotFoundException;
import com.example.Tour_Recommendation.model.Enum.Role;
import com.example.Tour_Recommendation.model.entity.User;
import com.example.Tour_Recommendation.repository.UserRepository;
import com.example.Tour_Recommendation.security.CustomUserDetails;
import com.example.Tour_Recommendation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public ApiResponse<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userRepository.findAll().stream()
                .map(UserResponse::from)
                .toList();
        return ApiResponse.success("Users fetched successfully", users);
    }

    @Override
    public ApiResponse<UserResponse> getUserById(Long id) {
        User user = findUserOrThrow(id);
        return ApiResponse.success("User fetched successfully", UserResponse.from(user));
    }

    @Override
    public ApiResponse<UserResponse> updateUser(Long id, UpdateUserRequest request) {
        User user = findUserOrThrow(id);

        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }
        if (request.getEmail() != null) {
            if (!request.getEmail().equals(user.getEmail())
                    && userRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Email already exists");
            }
            user.setEmail(request.getEmail());
        }
        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }
        if (request.getAvatarUrl() != null) {
            user.setAvatarUrl(request.getAvatarUrl());
        }
        if (request.getRole() != null) {
            validateRole(request.getRole());
            user.setRole(request.getRole());
        }

        User saved = userRepository.save(user);
        return ApiResponse.success("User updated successfully", UserResponse.from(saved));
    }

    @Override
    public ApiResponse<UserResponse> updateUserStatus(Long id, UpdateUserStatusRequest request) {
        User user = findUserOrThrow(id);
        user.setIsActive(request.getIsActive());

        User saved = userRepository.save(user);
        String message = Boolean.TRUE.equals(request.getIsActive())
                ? "User activated successfully"
                : "User deactivated successfully";
        return ApiResponse.success(message, UserResponse.from(saved));
    }

    @Override
    public ApiResponse<UserResponse> updateProfile(CustomUserDetails userDetails, UpdateProfileRequest request) {
        User user = findUserOrThrow(userDetails.getUser().getId());

        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }
        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }
        if (request.getAvatarUrl() != null) {
            user.setAvatarUrl(request.getAvatarUrl());
        }

        User saved = userRepository.save(user);
        return ApiResponse.success("Profile updated successfully", UserResponse.from(saved));
    }

    private User findUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private void validateRole(Role role) {
        if (role != Role.ROLE_USER && role != Role.ROLE_ADMIN) {
            throw new RuntimeException("Role must be ROLE_USER or ROLE_ADMIN");
        }
    }
}
