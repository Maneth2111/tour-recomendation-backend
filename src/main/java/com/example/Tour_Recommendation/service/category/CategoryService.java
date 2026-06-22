package com.example.Tour_Recommendation.service;

import com.example.Tour_Recommendation.common.ApiResponse;
import com.example.Tour_Recommendation.dto.request.category.CreateCategoryRequest;
import com.example.Tour_Recommendation.dto.request.category.UpdateCategoryRequest;
import com.example.Tour_Recommendation.dto.response.category.CategoryResponse;

import java.util.List;

public interface CategoryService {

    ApiResponse<List<CategoryResponse>> getAllCategories();

    ApiResponse<CategoryResponse> getCategoryById(Long id);

    ApiResponse<CategoryResponse> createCategory(CreateCategoryRequest request);

    ApiResponse<CategoryResponse> updateCategory(Long id, UpdateCategoryRequest request);

    ApiResponse<Void> deleteCategory(Long id);
}