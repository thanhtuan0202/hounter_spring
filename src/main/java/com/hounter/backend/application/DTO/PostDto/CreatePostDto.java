package com.hounter.backend.application.DTO.PostDto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Getter
@Setter
public class CreatePostDto {
    @NotBlank(message = "Không được để trống")
    private String title;

    @NotBlank(message = "Không được để trống")
    private String description;

    @NotNull(message = "Field must not be null")
    private Long category;

    @NotNull(message = "Field must not be null")
    private Integer price;

    @NotNull(message = "Field must not be null")
    private Integer area;

    @NotBlank(message = "Không được để trống")
    private String fullAddress;

    @NotBlank(message = "Không được để trống")
    private String city;

    @NotBlank(message = "Không được để trống")
    private String county;

    @NotBlank(message = "Không được để trống")
    private String district;

    List<String> imageUrls;
    
    @NotBlank(message = "Không được để trống")
    private String customerName;

    @NotBlank(message = "Không được để trống")
    @Pattern(regexp = "^\\d{10}$", message = "Must have 10 digit")
    private String customerPhone;

    @NotNull(message = "Field must not be null")
    private Long cost;

    @NotNull(message = "Field must not be null")
    private Integer days;

    private String note;
}
