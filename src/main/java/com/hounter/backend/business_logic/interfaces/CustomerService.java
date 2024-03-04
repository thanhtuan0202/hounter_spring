package com.hounter.backend.business_logic.interfaces;

import com.hounter.backend.application.DTO.CustomerDTO.CustomerResponseDTO;
import com.hounter.backend.application.DTO.CustomerDTO.UpdateInfoDTO;
import com.hounter.backend.application.DTO.PostDto.ShortPostResponse;

import java.util.List;
public interface CustomerService {
    CustomerResponseDTO getCustomerInfo(Long CustomerId);
    CustomerResponseDTO changeCustomerInfo(Long id, UpdateInfoDTO CustomerInfo) throws Exception;
    List<ShortPostResponse> getPostOfCustomer(Integer pageSize, Integer pageNo,String category,Long cost,Long customerId,String beginDate,String endDate);
}
