package com.example.Tour_Recommendation.dto.request.category;

import lombok.Data;
import jakarta.validation.constraints.Size;
@Data
public class UpdateCategoryRequest {
    @Size(max = 100)
    private String name;
}