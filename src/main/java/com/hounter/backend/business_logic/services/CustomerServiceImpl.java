package com.hounter.backend.business_logic.services;

import com.hounter.backend.application.DTO.CustomerDTO.CustomerResponseDTO;
import com.hounter.backend.application.DTO.CustomerDTO.UpdateInfoDTO;
import com.hounter.backend.application.DTO.PostDto.ShortPostResponse;
import com.hounter.backend.business_logic.entities.Customer;
import com.hounter.backend.business_logic.entities.Post;
import com.hounter.backend.business_logic.interfaces.CustomerService;
import com.hounter.backend.business_logic.interfaces.PostService;
import com.hounter.backend.business_logic.mapper.PostMapping;
import com.hounter.backend.data_access.repositories.CustomerRepository;
import com.hounter.backend.shared.enums.Status;
import com.hounter.backend.shared.exceptions.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PostService postService;

    @Override
    public CustomerResponseDTO getCustomerInfo(Long customerId) {
        Optional<Customer> optionalCustomer = this.customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            CustomerResponseDTO response = new CustomerResponseDTO();
            response.setId(customer.getId());
            response.setFull_name(customer.getFull_name());
            response.setUsername(customer.getUsername());
            response.setAddress(customer.getAddress());
            response.setBalance(customer.getBalance());
            response.setAvatar(customer.getAvatar());
            response.setPhone_number(customer.getPhoneNumber());

            return response;
        }
        return null;
    }

    @Override
    public CustomerResponseDTO changeCustomerInfo(Long id, UpdateInfoDTO CustomerInfo) {
        return null;
    }

    @Override
    public List<ShortPostResponse> getPostOfCustomer(Integer pageSize, Integer pageNo, String sortBy, String sortDir,
            String status, Long customerId) {
        Optional<Customer> optionalCustomer = this.customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            if (status != null) {
                for (Status item : Status.values()) {
                    if (item.name().equals(status)) {
                        return this.postService.getAllPost(pageSize, pageNo, sortBy, sortDir, customer, item);
                    }
                }
                return null;
            }
            return this.postService.getAllPost(pageSize, pageNo, sortBy, sortDir, customer);
        }
        throw new NotFoundException("Customer not found!", HttpStatus.UNAUTHORIZED);
    }
}
