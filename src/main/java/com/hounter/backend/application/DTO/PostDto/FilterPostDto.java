package com.hounter.backend.application.DTO.PostDto;

import jakarta.validation.constraints.NotBlank;

public class FilterPostDto {
    private String city;
    private String county;
    private String district;
    private Integer upperPrice;
    private Integer lowerPrice;
    @NotBlank
    private String type;
}
