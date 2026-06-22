package com.example.Tour_Recommendation.controllor;

import com.example.Tour_Recommendation.common.ApiResponse;
import com.example.Tour_Recommendation.dto.response.tour.TourImageResponse;
import com.example.Tour_Recommendation.service.TourImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tours/{tourId}/images")
@RequiredArgsConstructor
@Tag(name = "Tour Image")
public class TourImageController {

    private final TourImageService tourImageService;

    @GetMapping
    @Operation(summary = "Get tour images")
    public ApiResponse<List<TourImageResponse>> getTourImages(@PathVariable Long tourId) {
        return tourImageService.getTourImages(tourId);
    }
}
