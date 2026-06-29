package com.example.Tour_Recommendation.controllor;

import com.example.Tour_Recommendation.common.ApiResponse;
import com.example.Tour_Recommendation.dto.request.UpdateProfileRequest;
import com.example.Tour_Recommendation.dto.response.user.UserResponse;
import com.example.Tour_Recommendation.security.CustomUserDetails;
import com.example.Tour_Recommendation.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    @Operation(summary = "Get my profile (USER and ADMIN)")
    public ApiResponse<UserResponse> getMyProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return userService.getMyProfile(userDetails);
    }

    @PutMapping("/profile")
    @Operation(summary = "Edit my profile - update name, phone, or avatar_url (email and role are read-only)")
    public ApiResponse<UserResponse> updateProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody UpdateProfileRequest request
    ) {
        return userService.updateProfile(userDetails, request);
    }

    @PostMapping(value = "/profile/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload avatar and update my profile (tap to upload)")
    public ApiResponse<UserResponse> uploadAvatar(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam("file") MultipartFile file
    ) {
        return userService.uploadAvatar(userDetails, file);
    }
}
