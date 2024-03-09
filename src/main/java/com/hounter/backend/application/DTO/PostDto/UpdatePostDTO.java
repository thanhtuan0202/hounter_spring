package com.hounter.backend.application.DTO.PostDto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePostDTO {
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
    private String fullAddress;

    @NotBlank(message = "Không được để trống")
    private String city;

    @NotBlank(message = "Không được để trống")
    private String county;

    @NotBlank(message = "Không được để trống")
    private String district;

    @NotNull(message = "Không được để trống")
    private Long cost;

    @NotNull(message = "Không được để trống")
    private Integer days;

    List<String> addImages;
    
    List<String> deleteImages;
    
    @NotBlank(message = "Không được để trống")
    private String customerName;

    @NotBlank(message = "Không được để trống")
    @Pattern(regexp = "^\\d{10}$", message = "Must have 10 digit")
    private String customerPhone;

    private String note;
}
