package com.hounter.backend.business_logic.mapper;

import com.hounter.backend.application.DTO.AdminDTO.CustomerRestDTO;
import com.hounter.backend.application.DTO.CustomerDTO.CustomerResponseDTO;
import com.hounter.backend.business_logic.entities.Customer;
import com.hounter.backend.business_logic.entities.Payment;
import com.hounter.backend.shared.enums.PaymentStatus;

public class CustomerMapping {
    public static CustomerResponseDTO ResponeMapping(Customer customer){
        CustomerResponseDTO response = new CustomerResponseDTO();
        response.setId(customer.getId());
        response.setFull_name(customer.getFull_name());
        response.setUsername(customer.getUsername());
        response.setAddress(customer.getAddress());
        response.setBalance(customer.getBalance());
        response.setAvatar(customer.getAvatar());
        response.setPhone_number(customer.getPhoneNumber());
        response.setEmail(customer.getEmail());
        return response;
    }

    public static CustomerRestDTO adminMapping(Customer customer){
        CustomerRestDTO customerRes = new CustomerRestDTO();
        customerRes.setId(customer.getId());
        customerRes.setFullName(customer.getFull_name());
        customerRes.setPhoneNumber(customer.getPhoneNumber());
        customerRes.setIsActive(customer.getIsActive());
        customerRes.setCreateAt(customer.getCreateAt());
        customerRes.setNumOfPost(customer.getPosts().size());
        Integer total = 0;
        for(Payment payment: customer.getPayments()){
            if(payment.getStatus().equals(PaymentStatus.COMPLETE)){
                total += payment.getTotalPrice();
            }
        }
        customerRes.setTotalPayments(total);
        return customerRes;
    }
}
