package com.hounter.backend.business_logic.interfaces;


import com.hounter.backend.application.DTO.PaymentDTO.CreatePaymentDTO;
import com.hounter.backend.application.DTO.VNPayResDTO;
import com.hounter.backend.business_logic.entities.Payment;
import com.hounter.backend.business_logic.entities.PostCost;
import com.hounter.backend.shared.enums.PaymentStatus;

import java.io.IOException;
import java.util.List;

public interface PaymentService {
    void savePaymentOfPost(PostCost postCost, Long userId);
    void savePaymentOfPost(Payment payment, PostCost postCost);
    void confirmSuccessPayment(Long postId,String transactionNo, String bankCode, Integer amount);
    CreatePaymentDTO createPaymentOfPost(String xForwardedFor, String remoteAddr, Long postId, Long amount, Long userId) throws IOException;
    VNPayResDTO getPaymentInfo(String orderId, String transDate, String xForwardedFor, String remoteAddr) throws IOException;
    List<Payment> getListPaymentOfCustomer(String fromDate, String toDate, PaymentStatus status, String transactionId, Long customerId, Long postNum, Integer pageNo, Integer pageSize);
    Payment getPaymentByPostNum(Long postNum);
}
