package com.hounter.backend.application.DTO.AdminDTO;

import java.time.LocalDate;

import com.hounter.backend.shared.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResAdminDTO {
    Long postNum;
    PaymentStatus status;
    Integer totalPrice;
    String paymentMethod;
    LocalDate createAt;
    LocalDate paymentAt;
    String paymentInfo;
    String transactionId;
    String customerName;
}
