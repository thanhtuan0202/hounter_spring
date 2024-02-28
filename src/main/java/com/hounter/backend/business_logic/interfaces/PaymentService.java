package com.hounter.backend.business_logic.interfaces;


import com.hounter.backend.application.DTO.PaymentDTO.CreatePaymentDTO;
import com.hounter.backend.business_logic.entities.PostCost;

import java.io.IOException;

public interface PaymentService {
    public void savePaymentOfPost(PostCost postCost, Long userId);
    public CreatePaymentDTO createPaymentOfPost(String xForwardedFor, String remoteAddr) throws IOException;
    public void getPaymentInfo(String orderId, String transDate, String xForwardedFor, String remoteAddr) throws IOException;
}
