package com.example.Tour_Recommendation.controllor;
import com.example.Tour_Recommendation.common.ApiResponse;
import com.example.Tour_Recommendation.dto.response.tour.TourResponse;
import com.example.Tour_Recommendation.service.TourService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/tours")
@RequiredArgsConstructor
@Tag(name = "Tour")
public class TourController {
    private final TourService tourService;
    @GetMapping
    @Operation(summary = "Get all tours")
    public ApiResponse<List<TourResponse>> getAllTours() {
        return tourService.getAllActiveTours();
    }
    @GetMapping("/{id}")
    @Operation(summary = "Get tour by ID")
    public ApiResponse<TourResponse> getTourById(@PathVariable Long id) {
        return tourService.getTourById(id);
    }
    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Get tours by category ID")
    public ApiResponse<List<TourResponse>> getToursByCategoryId(@PathVariable Long categoryId) {
        return tourService.getToursByCategoryId(categoryId);
    }
}