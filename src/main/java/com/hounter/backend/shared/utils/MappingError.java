package com.hounter.backend.shared.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.hounter.backend.shared.binding.BindingBadRequest;

import java.util.List;
import java.util.ArrayList;

public class MappingError {
    public static List<BindingBadRequest> mappingError(BindingResult binding) {
        List<BindingBadRequest> error_lst = new ArrayList<>();
        for (FieldError field : binding.getFieldErrors()) {
            BindingBadRequest badRequest = new BindingBadRequest(field.getField(), field.getDefaultMessage());
            error_lst.add(badRequest);
        }
        return error_lst;
    }
}
