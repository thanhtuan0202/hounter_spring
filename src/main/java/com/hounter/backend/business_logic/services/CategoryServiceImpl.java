package com.hounter.backend.business_logic.services;

import com.hounter.backend.application.DTO.CategoryDTO.CategoryResponse;
import com.hounter.backend.business_logic.entities.Category;
import com.hounter.backend.business_logic.interfaces.CategoryService;
import com.hounter.backend.data_access.repositories.CategoryRepository;
import com.hounter.backend.shared.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories(){
        List< Category> categories = this.categoryRepository.findAll();
        List<CategoryResponse> responses = new ArrayList<>();
        for(Category category : categories){
            responses.add(new CategoryResponse(category.getId(), category.getName()));
        }
        return responses;
    }
    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Long id)
    {
        Optional<Category> optionalCategory = this.categoryRepository.findById(id);
        if(optionalCategory.isPresent()){
            Category category = optionalCategory.get();
            return new CategoryResponse(category.getId(), category.getName());
        }
        return null;
    }
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public CategoryResponse createCategory(String name){
        Category category = new Category();
        category.setName(name);
        Category saved =this.categoryRepository.save(category);
        return new CategoryResponse(saved.getId(), saved.getName());
    }
}
