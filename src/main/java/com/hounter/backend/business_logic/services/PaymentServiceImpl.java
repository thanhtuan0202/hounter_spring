package com.hounter.backend.business_logic.services;

import com.hounter.backend.business_logic.entities.Cost;
import com.hounter.backend.business_logic.entities.Payment;
import com.hounter.backend.business_logic.entities.PostCost;
import com.hounter.backend.business_logic.interfaces.PaymentService;
import com.hounter.backend.data_access.repositories.PaymentRepository;
import com.hounter.backend.shared.enums.PaymentStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void createPayment(PostCost postCost, Long userId) {
        Payment payment = new Payment();
        
        payment.setCreateAt(postCost.getDate());
        payment.setExpireAt(postCost.getDate().plusDays(7));
        Cost cost = postCost.getCost();
        Integer days = postCost.getActiveDays();
        Integer total_price = cost.getPrice() * days;
        if(days >= 7 && days < 30) {
            total_price -= total_price * cost.getDiscount_7days();
        }
        else if(days >= 30){
            total_price -= total_price * cost.getDiscount_30days();
        }
        payment.setTotalPrice(total_price);
        payment.setPostCost(postCost);
        payment.setStatus(PaymentStatus.waiting);
        Payment saved = this.paymentRepository.save(payment);
        
    }

    public void checkPaymentExpiration(){
        
    }
}
