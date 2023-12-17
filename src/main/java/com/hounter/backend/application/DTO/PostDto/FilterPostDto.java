package com.hounter.backend.application.DTO.PostDto;

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
    private Long category;
}
