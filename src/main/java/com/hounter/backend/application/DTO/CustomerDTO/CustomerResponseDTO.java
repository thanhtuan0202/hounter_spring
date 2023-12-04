package com.hounter.backend.application.DTO.CustomerDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDTO {
    private Long id;
    private String username;
    private String full_name;
    private String avatar;
    private Integer balance;
    private String phone_number;
    private String address;
}
