package com.example.Tour_Recommendation.service.serviceImp;

import com.example.Tour_Recommendation.common.ApiResponse;
import com.example.Tour_Recommendation.dto.request.tour.CreateTourImageRequest;
import com.example.Tour_Recommendation.dto.response.tour.TourImageResponse;
import com.example.Tour_Recommendation.exception.tour.TourImageNotFoundException;
import com.example.Tour_Recommendation.exception.tour.TourNotFoundException;
import com.example.Tour_Recommendation.model.entity.Tour;
import com.example.Tour_Recommendation.model.entity.TourImage;
import com.example.Tour_Recommendation.repository.TourImageRepository;
import com.example.Tour_Recommendation.repository.TourRepository;
import com.example.Tour_Recommendation.service.TourImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TourImageServiceImpl implements TourImageService {

    private final TourImageRepository tourImageRepository;
    private final TourRepository tourRepository;

    @Override
    public ApiResponse<List<TourImageResponse>> getTourImages(Long tourId) {
        findTourOrThrow(tourId);

        List<TourImageResponse> images = tourImageRepository.findByTourIdOrderByCreatedAtAsc(tourId)
                .stream()
                .map(TourImageResponse::from)
                .toList();

        return ApiResponse.success("Tour images fetched successfully", images);
    }

    @Override
    public ApiResponse<TourImageResponse> addTourImage(Long tourId, CreateTourImageRequest request) {
        Tour tour = findTourOrThrow(tourId);

        TourImage tourImage = TourImage.builder()
                .tour(tour)
                .imageUrl(request.getImageUrl())
                .build();

        TourImage saved = tourImageRepository.save(tourImage);
        return ApiResponse.success("Tour image added successfully", TourImageResponse.from(saved));
    }

    @Override
    public ApiResponse<Void> deleteTourImage(Long tourId, Long imageId) {
        findTourOrThrow(tourId);

        TourImage tourImage = tourImageRepository.findByIdAndTourId(imageId, tourId)
                .orElseThrow(() -> new TourImageNotFoundException(imageId));

        tourImageRepository.delete(tourImage);
        return ApiResponse.success("Tour image deleted successfully", null);
    }

    private Tour findTourOrThrow(Long tourId) {
        return tourRepository.findById(tourId)
                .orElseThrow(() -> new TourNotFoundException(tourId));
    }
}
