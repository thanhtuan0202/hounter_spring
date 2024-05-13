package com.hounter.backend.application.DTO.AdminDTO;

import com.hounter.backend.shared.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    LocalDateTime paymentAt;
    String paymentInfo;
    String transactionId;
    String customerName;
}
