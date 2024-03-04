package com.hounter.backend.business_logic.interfaces;


import com.hounter.backend.application.DTO.PaymentDTO.CreatePaymentDTO;
import com.hounter.backend.business_logic.entities.Customer;
import com.hounter.backend.business_logic.entities.Payment;
import com.hounter.backend.business_logic.entities.PostCost;

import java.io.IOException;
import java.util.List;

public interface PaymentService {
    public void savePaymentOfPost(PostCost postCost, Long userId);
    public void confirmSuccessPayment(Long postId,String transactionNo, String bankCode, Integer amount);
    public CreatePaymentDTO createPaymentOfPost(String xForwardedFor, String remoteAddr, Long postId, Long amount, Long userId) throws IOException;
    public void getPaymentInfo(String orderId, String transDate, String xForwardedFor, String remoteAddr) throws IOException;
    public List<Payment> getListPaymentOfCustomer(Customer customer, Integer pageNo, Integer pageSize);
    
}
