package com.hounter.backend.business_logic.services;

import com.hounter.backend.application.DTO.CustomerDTO.CustomerResponseDTO;
import com.hounter.backend.application.DTO.CustomerDTO.PostOfUserRes;
import com.hounter.backend.application.DTO.CustomerDTO.UpdateInfoDTO;
import com.hounter.backend.application.DTO.PaymentDTO.PaymentResDTO;
import com.hounter.backend.application.DTO.PostDto.ShortPostResponse;
import com.hounter.backend.business_logic.entities.Customer;
import com.hounter.backend.business_logic.entities.Payment;
import com.hounter.backend.business_logic.interfaces.CustomerService;
import com.hounter.backend.business_logic.interfaces.PaymentService;
import com.hounter.backend.business_logic.interfaces.PostService;
import com.hounter.backend.business_logic.mapper.CustomerMapping;
import com.hounter.backend.business_logic.mapper.PaymentMapping;
import com.hounter.backend.data_access.repositories.CustomerRepository;
import com.hounter.backend.shared.enums.PaymentStatus;
import com.hounter.backend.shared.enums.Status;
import com.hounter.backend.shared.exceptions.NotFoundException;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private PaymentService paymentService;
    @Override
    public CustomerResponseDTO getCustomerInfo(Long customerId) {
        Optional<Customer> optionalCustomer = this.customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            return CustomerMapping.ResponseMapping(customer);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public CustomerResponseDTO changeCustomerInfo(Long id, UpdateInfoDTO customerInfo) throws Exception {
        Optional<Customer> optionalCustomer = this.customerRepository.findById(id);
        if(optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setFull_name(customerInfo.getFull_name());
            customer.setAddress(customerInfo.getAddress());
            customer.setEmail(customerInfo.getEmail());
            customer.setPhoneNumber(customerInfo.getPhone_number());
            customer.setUpdateAt(LocalDate.now());
            customer.setAvatar(customerInfo.getAvatar());
            this.customerRepository.save(customer);
            return CustomerMapping.ResponseMapping(customer);
        }
        throw new IllegalIdentifierException("Customer not found");
    }

    @Override
    public List<ShortPostResponse> getPostOfCustomer(Integer pageSize, Integer pageNo, String category,String cost, Long customerId,String beginDate,String endDate, Status status) {
        Optional<Customer> optionalCustomer = this.customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            return this.postService.filterPostForUser(pageSize, pageNo, customer,category,cost,beginDate,endDate, status);
        }
        throw new NotFoundException("Customer not found!", HttpStatus.UNAUTHORIZED);
    }

    @Override
    public List<PaymentResDTO> getPaymentOfCustomer(String fromDate, String toDate, PaymentStatus status, String transactionId, Long customerId, Long postNum, Integer pageNo, Integer pageSize) {
        List<Payment> paymentList = this.paymentService.getListPaymentOfCustomer(fromDate, toDate, status, transactionId, customerId, postNum, pageNo, pageSize);
        List<PaymentResDTO> response = new ArrayList<>();
        for (Payment payment : paymentList) {
            response.add(PaymentMapping.toPaymentResMapping(payment));
        }
        return response;
    }

    @Override
    public PostOfUserRes getPostDetail(Long postId) {
        return this.postService.getPostDetailOfUser(postId);
    }
}
