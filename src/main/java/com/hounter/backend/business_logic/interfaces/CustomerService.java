package com.hounter.backend.business_logic.interfaces;

import com.hounter.backend.application.DTO.CustomerDTO.CustomerResponseDTO;
import com.hounter.backend.application.DTO.CustomerDTO.PostOfUserRes;
import com.hounter.backend.application.DTO.CustomerDTO.UpdateInfoDTO;
import com.hounter.backend.application.DTO.PaymentDTO.PaymentResDTO;
import com.hounter.backend.application.DTO.PostDto.ShortPostResponse;
import com.hounter.backend.shared.enums.PaymentStatus;
import com.hounter.backend.shared.enums.Status;

import java.util.List;
public interface CustomerService {
    CustomerResponseDTO getCustomerInfo(Long CustomerId);
    CustomerResponseDTO changeCustomerInfo(Long id, UpdateInfoDTO CustomerInfo) throws Exception;
    List<ShortPostResponse> getPostOfCustomer(Integer pageSize, Integer pageNo, String category, String cost, Long customerId, String beginDate, String endDate, Status status);
    List<PaymentResDTO> getPaymentOfCustomer(String fromDate, String toDate, PaymentStatus status, String transactionId, Long customerId, Long postNum, Integer pageNo, Integer pageSize);
    PostOfUserRes getPostDetail(Long postId);
}
