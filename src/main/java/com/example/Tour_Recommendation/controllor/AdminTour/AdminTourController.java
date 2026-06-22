package com.example.Tour_Recommendation.controllor;

import com.example.Tour_Recommendation.common.ApiResponse;
import com.example.Tour_Recommendation.dto.request.tour.CreateTourRequest;
import com.example.Tour_Recommendation.dto.request.tour.UpdateTourRequest;
import com.example.Tour_Recommendation.dto.response.tour.TourResponse;
import com.example.Tour_Recommendation.service.TourService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/tours")
@RequiredArgsConstructor
@Tag(name = "Admin Tour")
@SecurityRequirement(name = "Bearer Authentication")
public class AdminTourController {

    private final TourService tourService;

    @PostMapping
    @Operation(summary = "Create tour")
    public ApiResponse<TourResponse> createTour(
            @Valid @RequestBody CreateTourRequest request
    ) {
        return tourService.createTour(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update tour")
    public ApiResponse<TourResponse> updateTour(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTourRequest request
    ) {
        return tourService.updateTour(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete tour (soft delete)")
    public ApiResponse<Void> deleteTour(@PathVariable Long id) {
        return tourService.deleteTour(id);
    }
}