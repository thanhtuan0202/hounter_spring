package com.hounter.backend.application.DTO.PostDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreatePostDto {
    @NotBlank(message = "Không được để trống")
    private String title;

    @NotBlank(message = "Không được để trống")
    private String description;

    @NotBlank(message = "Không được để trống")
    private String category;

    @NotNull(message = "Không được để trống")
    private Integer price;

    @NotNull(message = "Không được để trống")
    private Integer area;

    @NotBlank(message = "Không được để trống")
    private String street;

    @NotNull
    private Integer wardId;

    List<String> imageUrls;
    
    @NotBlank(message = "Không được để trống")
    private String customerName;

    @NotBlank(message = "Không được để trống")
    @Pattern(regexp = "^\\d{10}$", message = "Must have 10 digit")
    private String customerPhone;

    @NotBlank(message = "Không được để trống")
    private String cost;

    @NotNull(message = "Không được để trống")
    private Integer days;

    private String note;
}
