package com.example.Tour_Recommendation.service.serviceImp;

import com.example.Tour_Recommendation.common.ApiResponse;
import com.example.Tour_Recommendation.dto.request.category.CreateCategoryRequest;
import com.example.Tour_Recommendation.dto.request.category.UpdateCategoryRequest;
import com.example.Tour_Recommendation.dto.response.category.CategoryResponse;
import com.example.Tour_Recommendation.exception.category.CategoryNotFoundException;
import com.example.Tour_Recommendation.model.entity.Category;
import com.example.Tour_Recommendation.repository.CategoryRepository;
import com.example.Tour_Recommendation.repository.TourRepository;
import com.example.Tour_Recommendation.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final TourRepository tourRepository;

    @Override
    public ApiResponse<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categories = categoryRepository.findAll().stream()
                .map(CategoryResponse::from)
                .toList();
        return ApiResponse.success("Categories fetched successfully", categories);
    }

    @Override
    public ApiResponse<CategoryResponse> getCategoryById(Long id) {
        Category category = findCategoryOrThrow(id);
        return ApiResponse.success("Category fetched successfully", CategoryResponse.from(category));
    }

    @Override
    public ApiResponse<CategoryResponse> createCategory(CreateCategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new RuntimeException("Category name already exists");
        }

        Category category = Category.builder()
                .name(request.getName())
                .build();

        Category saved = categoryRepository.save(category);
        return ApiResponse.success("Category created successfully", CategoryResponse.from(saved));
    }

    @Override
    public ApiResponse<CategoryResponse> updateCategory(Long id, UpdateCategoryRequest request) {
        Category category = findCategoryOrThrow(id);

        if (request.getName() != null) {
            if (!request.getName().equals(category.getName())
                    && categoryRepository.existsByName(request.getName())) {
                throw new RuntimeException("Category name already exists");
            }
            category.setName(request.getName());
        }

        Category saved = categoryRepository.save(category);
        return ApiResponse.success("Category updated successfully", CategoryResponse.from(saved));
    }

    @Override
    public ApiResponse<Void> deleteCategory(Long id) {
        Category category = findCategoryOrThrow(id);

        if (!tourRepository.findByCategoryId(id).isEmpty()) {
            throw new RuntimeException("Cannot delete category that has tours");
        }

        categoryRepository.delete(category);
        return ApiResponse.success("Category deleted successfully", null);
    }

    private Category findCategoryOrThrow(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }
}