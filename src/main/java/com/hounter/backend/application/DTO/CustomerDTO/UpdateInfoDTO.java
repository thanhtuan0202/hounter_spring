package com.hounter.backend.application.DTO.CustomerDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateInfoDTO {
    @NotBlank
    private String full_name;

    @NotBlank
    @Pattern(regexp = "^\\d{10}$", message = "Must have 10 digit")
    private String phone_number;

    @NotBlank
    private String address;
    
    @NotBlank
    private String email;

    @NotBlank
    private String avatar;
}
