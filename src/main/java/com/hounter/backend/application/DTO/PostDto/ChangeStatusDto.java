package com.hounter.backend.application.DTO.PostDto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeStatusDto {
    @NotBlank
    @Pattern(regexp = "waiting|active|inactive|delete", message = "Can't not change status")
    private String status;

}
