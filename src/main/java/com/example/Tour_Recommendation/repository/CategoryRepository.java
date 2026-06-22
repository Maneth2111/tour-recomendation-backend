package com.example.Tour_Recommendation.repository;

import com.example.Tour_Recommendation.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByName(String name);
}
