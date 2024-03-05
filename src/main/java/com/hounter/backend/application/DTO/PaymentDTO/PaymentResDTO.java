package com.hounter.backend.application.DTO.PaymentDTO;

import com.hounter.backend.shared.enums.PaymentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class PaymentResDTO {
    private PaymentStatus status;
    private Integer totalPrice;
    private String paymentMethod;
    private LocalDate createAt;
    private LocalDate paymentAt;
    private String transactionId;
    private Long postId;
    private String postTitle;
    private String costType;
    private Integer activeDays;
}
