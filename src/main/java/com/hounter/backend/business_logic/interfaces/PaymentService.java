package com.hounter.backend.business_logic.interfaces;


import com.hounter.backend.application.DTO.PaymentDTO.CreatePaymentDTO;
import com.hounter.backend.application.DTO.VNPayResDTO;
import com.hounter.backend.business_logic.entities.Payment;
import com.hounter.backend.business_logic.entities.Post;
import com.hounter.backend.business_logic.entities.PostCost;
import com.hounter.backend.shared.enums.PaymentStatus;
import org.springframework.web.util.UriComponents;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface PaymentService {
    void savePaymentOfPost(PostCost postCost, Long userId);
    void updatePaymentOfPost(PostCost postCost);
    UriComponents confirmPayment(String vnpResponseCode, String vnPayTxnRef, String transactionNo, String bankCode, Integer amount, String vnp_PayDate);
    CreatePaymentDTO createPaymentOfPost(Long postId, Long amount, Long userId) throws IOException;
    VNPayResDTO getPaymentInfo(String orderId, String transDate) throws IOException;
    List<Payment> getListPaymentOfCustomer(String fromDate, String toDate, PaymentStatus status, String transactionId, Long customerId, Long postNum, Integer pageNo, Integer pageSize);
    void handlePaymentExpire(LocalDate date );
    void handleRemindPayment(LocalDate date);
    Payment getPaymentByPost(Post post);
}
