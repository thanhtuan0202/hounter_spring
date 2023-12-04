package com.hounter.backend.business_logic.interfaces;

import com.hounter.backend.application.DTO.CustomerDTO.CustomerResponseDTO;
import com.hounter.backend.application.DTO.CustomerDTO.UpdateInfoDTO;
import com.hounter.backend.application.DTO.PostDto.ShortPostResponse;

import java.util.List;
public interface CustomerService {
    public CustomerResponseDTO getCustomerInfo(Long CustomerId);
    public CustomerResponseDTO changeCustomerInfo(Long id, UpdateInfoDTO CustomerInfo);
    public List<ShortPostResponse> getPostOfCustomer(Integer pageSize, Integer pageNo, String sortBy, String sortDir,String status,Long customerId);
}
