package com.example.Tour_Recommendation.dto.request.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import jakarta.validation.constraints.Size;
@Data
public class CreateCategoryRequest {
    @NotBlank
    @Size(max = 100)
    private String name;
}