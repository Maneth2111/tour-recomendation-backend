package com.example.Tour_Recommendation.service;

import com.example.Tour_Recommendation.common.ApiResponse;
import com.example.Tour_Recommendation.dto.request.favorite.AddFavoriteTourRequest;
import com.example.Tour_Recommendation.dto.response.favorite.FavoriteStatusResponse;
import com.example.Tour_Recommendation.dto.response.favorite.FavoriteTourResponse;
import com.example.Tour_Recommendation.security.CustomUserDetails;

import java.util.List;

public interface FavoriteTourService {

    ApiResponse<FavoriteTourResponse> addFavorite(CustomUserDetails userDetails, AddFavoriteTourRequest request);

    ApiResponse<Void> removeFavorite(CustomUserDetails userDetails, Long tourId);

    ApiResponse<List<FavoriteTourResponse>> getMyFavorites(CustomUserDetails userDetails);

    ApiResponse<FavoriteStatusResponse> checkFavorite(CustomUserDetails userDetails, Long tourId);
}
