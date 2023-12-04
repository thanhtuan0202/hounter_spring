package com.hounter.backend.business_logic.interfaces;


import com.hounter.backend.business_logic.entities.PostCost;

public interface PaymentService {
    public void createPayment(PostCost postCost, Long userId);
}
