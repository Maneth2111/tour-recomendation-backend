package com.example.Tour_Recommendation.service.serviceImp;

import com.example.Tour_Recommendation.common.ApiResponse;
import com.example.Tour_Recommendation.dto.request.GoogleAuthRequest;
import com.example.Tour_Recommendation.dto.LoginRequest;
import com.example.Tour_Recommendation.dto.RegisterRequest;
import com.example.Tour_Recommendation.dto.response.LoginResponse;
import com.example.Tour_Recommendation.dto.response.UserResponse;
import com.example.Tour_Recommendation.model.Enum.Role;
import com.example.Tour_Recommendation.model.entity.User;
import com.example.Tour_Recommendation.repository.UserRepository;
import com.example.Tour_Recommendation.security.CustomUserDetails;
import com.example.Tour_Recommendation.security.GoogleTokenVerifier;
import com.example.Tour_Recommendation.security.GoogleUserPayload;
import com.example.Tour_Recommendation.security.JwtService;
import com.example.Tour_Recommendation.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final GoogleTokenVerifier googleTokenVerifier;

    @Override
    public ApiResponse<UserResponse> register(RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Password and Confirm Password do not match");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Role role = request.getRole();
        if (role != Role.ROLE_USER && role != Role.ROLE_ADMIN) {
            throw new RuntimeException("Role must be ROLE_USER or ROLE_ADMIN");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .isActive(true)
                .build();

        userRepository.save(user);

        return ApiResponse.success("User created successfully", UserResponse.from(user));
    }

    @Override
    public ApiResponse<LoginResponse> login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        String token = jwtService.generateToken(userDetails);

        return ApiResponse.success("Login successful", LoginResponse.from(user, token));
    }

    @Override
    public ApiResponse<LoginResponse> googleAuth(GoogleAuthRequest request) {
        GoogleUserPayload googleUser = googleTokenVerifier.verify(request.getIdToken());

        User user = userRepository.findByGoogleId(googleUser.googleId()).orElse(null);
        boolean isNewUser = false;

        if (user == null) {
            Optional<User> existingByEmail = userRepository.findByEmail(googleUser.email());
            if (existingByEmail.isPresent()) {
                user = linkGoogleAccount(existingByEmail.get(), googleUser);
            } else {
                user = createGoogleUser(googleUser);
                isNewUser = true;
            }
        }

        if (!Boolean.TRUE.equals(user.getIsActive())) {
            throw new RuntimeException("Account is inactive");
        }

        CustomUserDetails userDetails = new CustomUserDetails(user);
        String token = jwtService.generateToken(userDetails);
        String message = isNewUser
                ? "Registered with Google successfully"
                : "Login with Google successful";

        return ApiResponse.success(message, LoginResponse.from(user, token));
    }

    private User createGoogleUser(GoogleUserPayload googleUser) {
        User user = User.builder()
                .fullName(googleUser.name() != null ? googleUser.name() : googleUser.email())
                .email(googleUser.email())
                .googleId(googleUser.googleId())
                .avatarUrl(googleUser.picture())
                .role(Role.ROLE_USER)
                .isActive(true)
                .build();

        return userRepository.save(user);
    }

    private User linkGoogleAccount(User user, GoogleUserPayload googleUser) {
        user.setGoogleId(googleUser.googleId());
        if (user.getAvatarUrl() == null && googleUser.picture() != null) {
            user.setAvatarUrl(googleUser.picture());
        }
        if ((user.getFullName() == null || user.getFullName().isBlank()) && googleUser.name() != null) {
            user.setFullName(googleUser.name());
        }
        return userRepository.save(user);
    }

    @Override
    public ApiResponse<UserResponse> getMe(CustomUserDetails userDetails) {
        return ApiResponse.success("User fetched successfully", UserResponse.from(userDetails.getUser()));
    }
}
