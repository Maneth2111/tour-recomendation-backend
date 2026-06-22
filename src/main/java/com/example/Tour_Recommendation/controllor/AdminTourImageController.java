package com.example.Tour_Recommendation.controllor;

import com.example.Tour_Recommendation.common.ApiResponse;
import com.example.Tour_Recommendation.dto.request.tour.CreateTourImageRequest;
import com.example.Tour_Recommendation.dto.response.tour.TourImageResponse;
import com.example.Tour_Recommendation.service.TourImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/tours/{tourId}/images")
@RequiredArgsConstructor
@Tag(name = "Admin Tour Image")
@SecurityRequirement(name = "Bearer Authentication")
public class AdminTourImageController {

    private final TourImageService tourImageService;

    @PostMapping
    @Operation(summary = "Add tour image by URL")
    public ApiResponse<TourImageResponse> addTourImage(
            @PathVariable Long tourId,
            @Valid @RequestBody CreateTourImageRequest request
    ) {
        return tourImageService.addTourImage(tourId, request);
    }

    @DeleteMapping("/{imageId}")
    @Operation(summary = "Delete tour image")
    public ApiResponse<Void> deleteTourImage(
            @PathVariable Long tourId,
            @PathVariable Long imageId
    ) {
        return tourImageService.deleteTourImage(tourId, imageId);
    }
}
