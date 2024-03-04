package com.hounter.backend.application.DTO.CustomerDTO;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateInfoDTO {
    private String full_name;
    @Pattern(regexp = "^\\d{10}$", message = "Must have 10 digit")
    private String phone_number;
    private String address;
    private String email;
    private String avatar;
}
