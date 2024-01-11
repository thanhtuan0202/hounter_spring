package com.hounter.backend.business_logic.mapper;

import com.hounter.backend.application.DTO.CustomerDTO.CustomerResponseDTO;
import com.hounter.backend.business_logic.entities.Customer;

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
}
