package com.hounter.backend.business_logic.mapper;

import com.hounter.backend.application.DTO.AdminDTO.CustomerListResDTO;
import com.hounter.backend.application.DTO.AdminDTO.CustomerRestDTO;
import com.hounter.backend.application.DTO.CustomerDTO.CustomerResponseDTO;
import com.hounter.backend.business_logic.entities.Customer;
import com.hounter.backend.business_logic.entities.Payment;
import com.hounter.backend.shared.enums.PaymentStatus;

public class CustomerMapping {
    public static CustomerResponseDTO ResponseMapping(Customer customer){
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
        customerRes.setUsername(customer.getUsername());
        customerRes.setFullName(customer.getFull_name());
        customerRes.setAddress(customer.getAddress());
        customerRes.setPhone(customer.getPhoneNumber());
        customerRes.setIsActive(customer.getIsActive());
        customerRes.setCreateAt(customer.getCreateAt());
        customerRes.setAvatar(customer.getAvatar());
        customerRes.setEmail(customer.getEmail());
        Integer total = 0;
        for(Payment payment: customer.getPayments()){
            if(payment.getStatus().equals(PaymentStatus.COMPLETE)){
                total += payment.getTotalPrice();
            }
        }
        customerRes.setTotalPayments(total);
        return customerRes;
    }

    public static CustomerListResDTO adminListMapping(Customer customer){
        CustomerListResDTO customerRes = new CustomerListResDTO();
        customerRes.setId(customer.getId());
        customerRes.setUsername(customer.getUsername());
        customerRes.setFullName(customer.getFull_name());
        customerRes.setEmail(customer.getEmail());
        customerRes.setAddress(customer.getAddress());
        customerRes.setCreateAt(customer.getCreateAt());
        if (customer.getIsActive()){
            customerRes.setIsActive("Hoạt động");
        } else {
            customerRes.setIsActive("Không hoạt động");
        }
        return customerRes;
    }
}
