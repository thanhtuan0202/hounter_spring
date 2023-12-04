package com.hounter.backend.shared.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BindingBadRequest {
    private String field;
    private String message;

    public BindingBadRequest(String field, String message) {
        this.field = field;
        this.message = message;
    }
}
