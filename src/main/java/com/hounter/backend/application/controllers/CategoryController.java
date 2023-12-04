package com.hounter.backend.application.controllers;

import com.hounter.backend.application.DTO.CategoryDTO.CategoryResponse;
import com.hounter.backend.application.DTO.CategoryDTO.CreateCategoryDto;
import com.hounter.backend.business_logic.interfaces.CategoryService;
import com.hounter.backend.shared.binding.BindingBadRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(("/categories"))
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<?> getCategory(){
        List<CategoryResponse> responses = this.categoryService.getAllCategories();
        if(responses == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") Long id){
        CategoryResponse responses = this.categoryService.getCategoryById(id);
        if(responses == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(responses);
    }

    @PostMapping()
    public ResponseEntity<?> createCategory(@Valid @RequestBody CreateCategoryDto dto, BindingResult binding){
        if (binding.hasErrors()) {
            List<BindingBadRequest> error_lst = new ArrayList<>();
            for (FieldError field : binding.getFieldErrors()) {
                BindingBadRequest badRequest = new BindingBadRequest(field.getField(), field.getDefaultMessage());
                error_lst.add(badRequest);
            }
            return ResponseEntity.badRequest().body(error_lst);
        }
        CategoryResponse categoryResponse = this.categoryService.createCategory(dto.getName());
        return ResponseEntity.ok(categoryResponse);
    }
}
