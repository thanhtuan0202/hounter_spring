package com.hounter.backend.application.DTO.PostDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShortCustomer {
    private Long id;
    private String fullName;
    private String avatar;
}
