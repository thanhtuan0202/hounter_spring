package com.hounter.backend.application.DTO.PaymentDTO;

import java.time.LocalDate;

import com.hounter.backend.shared.enums.PaymentStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PaymentResDTO {
    PaymentStatus status;
    Integer totalPrice;
    String paymentMethod;
    LocalDate createAt;
    LocalDate paymentAt;
    String transactionId;
    
}
