package com.example.Tour_Recommendation.service.serviceImp;

import com.example.Tour_Recommendation.common.ApiResponse;
import com.example.Tour_Recommendation.dto.request.favorite.AddFavoriteTourRequest;
import com.example.Tour_Recommendation.dto.response.favorite.FavoriteStatusResponse;
import com.example.Tour_Recommendation.dto.response.favorite.FavoriteTourResponse;
import com.example.Tour_Recommendation.exception.favorite.FavoriteTourNotFoundException;
import com.example.Tour_Recommendation.exception.tour.TourNotFoundException;
import com.example.Tour_Recommendation.model.entity.FavoriteTour;
import com.example.Tour_Recommendation.model.entity.Tour;
import com.example.Tour_Recommendation.model.entity.User;
import com.example.Tour_Recommendation.repository.FavoriteTourRepository;
import com.example.Tour_Recommendation.repository.TourRepository;
import com.example.Tour_Recommendation.security.CustomUserDetails;
import com.example.Tour_Recommendation.service.FavoriteTourService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteTourServiceImpl implements FavoriteTourService {

    private final FavoriteTourRepository favoriteTourRepository;
    private final TourRepository tourRepository;

    @Override
    @Transactional
    public ApiResponse<FavoriteTourResponse> addFavorite(
            CustomUserDetails userDetails,
            AddFavoriteTourRequest request
    ) {
        User user = userDetails.getUser();
        Tour tour = tourRepository.findById(request.getTourId())
                .orElseThrow(() -> new TourNotFoundException(request.getTourId()));

        if (!Boolean.TRUE.equals(tour.getIsActive())) {
            throw new RuntimeException("Tour is not available");
        }

        if (favoriteTourRepository.existsByUserIdAndTourId(user.getId(), tour.getId())) {
            throw new RuntimeException("Tour is already in favorites");
        }

        FavoriteTour favoriteTour = FavoriteTour.builder()
                .user(user)
                .tour(tour)
                .build();

        FavoriteTour saved = favoriteTourRepository.save(favoriteTour);
        return ApiResponse.success("Tour added to favorites", FavoriteTourResponse.from(saved));
    }

    @Override
    @Transactional
    public ApiResponse<Void> removeFavorite(CustomUserDetails userDetails, Long tourId) {
        Long userId = userDetails.getUser().getId();

        if (!favoriteTourRepository.existsByUserIdAndTourId(userId, tourId)) {
            throw new FavoriteTourNotFoundException(tourId);
        }

        favoriteTourRepository.deleteByUserIdAndTourId(userId, tourId);
        return ApiResponse.success("Tour removed from favorites", null);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<FavoriteTourResponse>> getMyFavorites(CustomUserDetails userDetails) {
        List<FavoriteTourResponse> favorites = favoriteTourRepository
                .findByUserIdOrderByCreatedAtDesc(userDetails.getUser().getId())
                .stream()
                .map(FavoriteTourResponse::from)
                .toList();

        return ApiResponse.success("Favorite tours fetched successfully", favorites);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<FavoriteStatusResponse> checkFavorite(CustomUserDetails userDetails, Long tourId) {
        if (!tourRepository.existsById(tourId)) {
            throw new TourNotFoundException(tourId);
        }

        boolean isFavorite = favoriteTourRepository.existsByUserIdAndTourId(
                userDetails.getUser().getId(),
                tourId
        );

        FavoriteStatusResponse response = FavoriteStatusResponse.builder()
                .tourId(tourId)
                .isFavorite(isFavorite)
                .build();

        return ApiResponse.success("Favorite status fetched successfully", response);
    }
}
