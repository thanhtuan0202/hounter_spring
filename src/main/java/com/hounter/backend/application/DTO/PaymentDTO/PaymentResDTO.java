package com.hounter.backend.application.DTO.PaymentDTO;

import com.hounter.backend.shared.enums.PaymentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class PaymentResDTO {
    private PaymentStatus status;
    private Integer totalPrice;
    private String paymentMethod;
    private String paymentInfo;
    private LocalDate createAt;
    private LocalDateTime paymentAt;
    private String transactionId;
    private Long postId;
    private String postTitle;
    private String costType;
    private Integer activeDays;
}
