package com.hounter.backend.application.DTO.PaymentDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentDTO {
    private String url;
    private String status;
    private String message;
}
