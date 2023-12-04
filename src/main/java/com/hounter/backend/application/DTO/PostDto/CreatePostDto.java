package com.hounter.backend.application.DTO.PostDto;

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

    @NotBlank(message = "Không được để trống")
    private Long category;

    @NotBlank(message = "Không được để trống")
    private Integer price;

    @NotBlank(message = "Không được để trống")
    private Integer area;

    @NotBlank(message = "Không được để trống")
    private String fullAddress;

    @NotBlank(message = "Không được để trống")
    private String city;

    @NotBlank(message = "Không được để trống")
    private String county;

    @NotBlank(message = "Không được để trống")
    private String district;

    @NotBlank(message = "Không được để trống")
    List<String> imageUrls;
    
    @NotBlank(message = "Không được để trống")
    private String customerName;

    @NotBlank(message = "Không được để trống")
    @Pattern(regexp = "^\\d{10}$", message = "Must have 10 digit")
    private String customerPhone;

    @NotBlank
    private Long cost;

    @NotBlank
    private Integer days;

    private String note;
}
