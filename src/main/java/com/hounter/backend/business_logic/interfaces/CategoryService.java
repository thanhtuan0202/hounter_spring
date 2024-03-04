package com.hounter.backend.business_logic.interfaces;

import com.hounter.backend.application.DTO.CategoryDTO.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAllCategories();
    CategoryResponse getCategoryById(Long id);
    CategoryResponse createCategory(String name);
}
