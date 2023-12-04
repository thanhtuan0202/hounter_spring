package com.hounter.backend.application.DTO.CustomerDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    private String phone_number;
    @NotBlank
    private String address;
}
