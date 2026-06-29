package com.example.Tour_Recommendation.controllor;

import com.example.Tour_Recommendation.common.ApiResponse;
import com.example.Tour_Recommendation.dto.request.UpdateUserRequest;
import com.example.Tour_Recommendation.dto.request.UpdateUserStatusRequest;
import com.example.Tour_Recommendation.dto.response.user.UserResponse;
import com.example.Tour_Recommendation.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@Tag(name = "Admin User")
@SecurityRequirement(name = "Bearer Authentication")
public class AdminUserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get all users")
    public ApiResponse<List<UserResponse>> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    public ApiResponse<UserResponse> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user by ID (admin)")
    public ApiResponse<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request
    ) {
        return userService.updateUser(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Activate or deactivate user by ID")
    public ApiResponse<UserResponse> updateUserStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserStatusRequest request
    ) {
        return userService.updateUserStatus(id, request);
    }
}
