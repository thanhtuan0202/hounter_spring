package com.hounter.backend.business_logic.mapper;

import com.hounter.backend.application.DTO.AdminDTO.PaymentResAdminDTO;
import com.hounter.backend.business_logic.entities.Payment;

public class AdminMapping {
    public static PaymentResAdminDTO mappingPayment(Payment payment){
        PaymentResAdminDTO paymentResAdminDTO = new PaymentResAdminDTO();
        paymentResAdminDTO.setPostNum(payment.getPostNum());
        paymentResAdminDTO.setTotalPrice(payment.getTotalPrice());
        paymentResAdminDTO.setCreateAt(payment.getCreateAt());
        paymentResAdminDTO.setPaymentAt(payment.getPaymentDate());
        paymentResAdminDTO.setPaymentMethod(payment.getPaymentMethod());
        paymentResAdminDTO.setTransactionId(payment.getPaymentId());
        paymentResAdminDTO.setCustomerName(payment.getCustomer().getFull_name());
        paymentResAdminDTO.setStatus(payment.getStatus());
        paymentResAdminDTO.setPaymentInfo(payment.getPaymentInfo());
        return paymentResAdminDTO;
    }
}
