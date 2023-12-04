package com.hounter.backend.business_logic.interfaces;

import com.hounter.backend.application.DTO.CategoryDTO.CategoryResponse;

import java.util.List;

public interface CategoryService {
    public List<CategoryResponse> getAllCategories();
    public CategoryResponse getCategoryById(Long id);
    public CategoryResponse createCategory(String name);
}
