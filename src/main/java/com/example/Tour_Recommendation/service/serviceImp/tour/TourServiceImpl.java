package com.example.Tour_Recommendation.service.serviceImp;

import com.example.Tour_Recommendation.common.ApiResponse;
import com.example.Tour_Recommendation.dto.request.tour.CreateTourRequest;
import com.example.Tour_Recommendation.dto.request.tour.UpdateTourRequest;
import com.example.Tour_Recommendation.dto.response.tour.TourResponse;
import com.example.Tour_Recommendation.exception.category.CategoryNotFoundException;
import com.example.Tour_Recommendation.exception.tour.TourNotFoundException;
import com.example.Tour_Recommendation.model.entity.Category;
import com.example.Tour_Recommendation.model.entity.Tour;
import com.example.Tour_Recommendation.repository.CategoryRepository;
import com.example.Tour_Recommendation.repository.TourRepository;
import com.example.Tour_Recommendation.service.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ApiResponse<List<TourResponse>> getAllActiveTours() {
        List<TourResponse> tours = tourRepository.findAll().stream()
                .filter(tour -> Boolean.TRUE.equals(tour.getIsActive()))
                .map(TourResponse::from)
                .toList();
        return ApiResponse.success("Tours fetched successfully", tours);
    }

    @Override
    public ApiResponse<TourResponse> getTourById(Long id) {
        Tour tour = findTourOrThrow(id);

        if (!Boolean.TRUE.equals(tour.getIsActive())) {
            throw new TourNotFoundException(id);
        }

        return ApiResponse.success("Tour fetched successfully", TourResponse.from(tour));
    }

    @Override
    public ApiResponse<List<TourResponse>> getToursByCategoryId(Long categoryId) {
        findCategoryOrThrow(categoryId);

        List<TourResponse> tours = tourRepository
                .findByCategoryIdAndIsActiveTrue(categoryId)
                .stream()
                .map(TourResponse::from)
                .toList();

        return ApiResponse.success("Tours fetched successfully", tours);
    }

    @Override
    public ApiResponse<TourResponse> createTour(CreateTourRequest request) {
        Category category = findCategoryOrThrow(request.getCategoryId());

        if (tourRepository.existsByTitle(request.getTitle())) {
            throw new RuntimeException("Tour title already exists");
        }

        Tour tour = Tour.builder()
                .category(category)
                .title(request.getTitle())
                .description(request.getDescription())
                .location(request.getLocation())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .durationHours(request.getDurationHours())
                .price(request.getPrice())
                .maxPeople(request.getMaxPeople())
                .imageCover(request.getImageCover())
                .build();

        Tour saved = tourRepository.save(tour);
        return ApiResponse.success("Tour created successfully", TourResponse.from(saved));
    }

    @Override
    public ApiResponse<TourResponse> updateTour(Long id, UpdateTourRequest request) {
        Tour tour = findTourOrThrow(id);

        if (request.getCategoryId() != null) {
            Category category = findCategoryOrThrow(request.getCategoryId());
            tour.setCategory(category);
        }
        if (request.getTitle() != null) {
            if (!request.getTitle().equals(tour.getTitle())
                    && tourRepository.existsByTitle(request.getTitle())) {
                throw new RuntimeException("Tour title already exists");
            }
            tour.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            tour.setDescription(request.getDescription());
        }
        if (request.getLocation() != null) {
            tour.setLocation(request.getLocation());
        }
        if (request.getLatitude() != null) {
            tour.setLatitude(request.getLatitude());
        }
        if (request.getLongitude() != null) {
            tour.setLongitude(request.getLongitude());
        }
        if (request.getDurationHours() != null) {
            tour.setDurationHours(request.getDurationHours());
        }
        if (request.getPrice() != null) {
            tour.setPrice(request.getPrice());
        }
        if (request.getMaxPeople() != null) {
            tour.setMaxPeople(request.getMaxPeople());
        }
        if (request.getImageCover() != null) {
            tour.setImageCover(request.getImageCover());
        }
        if (request.getIsActive() != null) {
            tour.setIsActive(request.getIsActive());
        }

        Tour saved = tourRepository.save(tour);
        return ApiResponse.success("Tour updated successfully", TourResponse.from(saved));
    }

    @Override
    public ApiResponse<Void> deleteTour(Long id) {
        Tour tour = findTourOrThrow(id);
        tour.setIsActive(false);
        tourRepository.save(tour);
        return ApiResponse.success("Tour deleted successfully", null);
    }

    private Category findCategoryOrThrow(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    private Tour findTourOrThrow(Long id) {
        return tourRepository.findById(id)
                .orElseThrow(() -> new TourNotFoundException(id));
    }
}