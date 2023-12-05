package com.hounter.backend.application.DTO.FavoriteDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteResponse {
    private Long id;
    private String title;
    private Integer price;
    private Integer area;
    private String address;
    private LocalDate createAt;
    private LocalDate expireAt;
    private Long category;
    private Long cost;
    private String image;
}  
