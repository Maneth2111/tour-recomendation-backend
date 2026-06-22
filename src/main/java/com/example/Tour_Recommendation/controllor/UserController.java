package com.example.Tour_Recommendation.controllor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

    @GetMapping("/profile")
    @Operation(summary = "User-only endpoint (USER or ADMIN)")
    public String userProfile() {
        return "Hello USER - you have access to user endpoints";
    }
}
