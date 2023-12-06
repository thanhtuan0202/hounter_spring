package com.hounter.backend.application.DTO.PostDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilterPostDto {
    private String city;
    private String county;
    private String district;
    private Integer upperPrice;
    private Integer lowerPrice;
    @NotBlank
    private String type;
}
