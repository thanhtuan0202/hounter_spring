package com.hounter.backend.business_logic.mapper;

import com.hounter.backend.application.DTO.PaymentDTO.PaymentResDTO;
import com.hounter.backend.business_logic.entities.Payment;

public class PaymentMapping {
    public static PaymentResDTO toPaymentResMapping(Payment payment){
        PaymentResDTO response = new PaymentResDTO();
        response.setTotalPrice(payment.getTotalPrice());
        response.setCreateAt(payment.getCreateAt());
        response.setStatus(payment.getStatus());
        response.setPaymentAt(payment.getPaymentDate());
        response.setTransactionId(payment.getVnPayTransactionId());
        response.setPaymentMethod(payment.getPaymentMethod());
        response.setPostId(payment.getPostCost().getPost().getId());
        response.setPostTitle(payment.getPostCost().getPost().getTitle());
        response.setCostType(payment.getPostCost().getCost().getName());
        response.setActiveDays(payment.getPostCost().getActiveDays());
        response.setPaymentInfo(payment.getPaymentInfo());
        return response;
    }
}
