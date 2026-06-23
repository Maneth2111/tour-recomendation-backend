package com.example.Tour_Recommendation.controllor;

import com.example.Tour_Recommendation.common.ApiResponse;
import com.example.Tour_Recommendation.dto.request.favorite.AddFavoriteTourRequest;
import com.example.Tour_Recommendation.dto.response.favorite.FavoriteStatusResponse;
import com.example.Tour_Recommendation.dto.response.favorite.FavoriteTourResponse;
import com.example.Tour_Recommendation.security.CustomUserDetails;
import com.example.Tour_Recommendation.service.FavoriteTourService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
@Tag(name = "Favorite Tour")
@SecurityRequirement(name = "Bearer Authentication")
public class FavoriteTourController {

    private final FavoriteTourService favoriteTourService;

    @PostMapping
    @Operation(summary = "Add tour to favorites")
    public ApiResponse<FavoriteTourResponse> addFavorite(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody AddFavoriteTourRequest request
    ) {
        return favoriteTourService.addFavorite(userDetails, request);
    }

    @GetMapping
    @Operation(summary = "Get all favorite tours for current user")
    public ApiResponse<List<FavoriteTourResponse>> getMyFavorites(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return favoriteTourService.getMyFavorites(userDetails);
    }

    @GetMapping("/check/{tourId}")
    @Operation(summary = "Check if tour is favorited by current user")
    public ApiResponse<FavoriteStatusResponse> checkFavorite(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long tourId
    ) {
        return favoriteTourService.checkFavorite(userDetails, tourId);
    }

    @DeleteMapping("/tour/{tourId}")
    @Operation(summary = "Remove tour from favorites")
    public ApiResponse<Void> removeFavorite(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long tourId
    ) {
        return favoriteTourService.removeFavorite(userDetails, tourId);
    }
}
