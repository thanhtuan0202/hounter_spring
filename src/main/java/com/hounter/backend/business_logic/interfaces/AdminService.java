package com.hounter.backend.business_logic.interfaces;

import com.hounter.backend.application.DTO.AdminDTO.CreateStaffDTO;
import com.hounter.backend.application.DTO.AdminDTO.CustomerRestDTO;
import com.hounter.backend.application.DTO.AdminDTO.CustomerListResDTO;
import com.hounter.backend.application.DTO.AdminDTO.PaymentResAdminDTO;
import com.hounter.backend.application.DTO.AdminDTO.StaffResDTO;
import com.hounter.backend.business_logic.entities.Payment;
import com.hounter.backend.shared.enums.PaymentStatus;
import com.hounter.backend.shared.enums.Status;

import java.util.List;

public interface AdminService {
    List<CustomerListResDTO> getListCustomer(Integer pageNo, Integer pageSize);
    CustomerRestDTO getCustomerInfo(Long customerId);
    boolean createStaff(CreateStaffDTO createStaffDTO);
    List<StaffResDTO> getListStaff(Integer pageNo, Integer pageSize);
    StaffResDTO getStaffInfo(Long staffId);
    boolean deleteAccount(Long accountId);
    boolean updatePostStatus(Long postId, Status status);
    List<PaymentResAdminDTO> getPaymentsAdmin(String fromDate, String toDate, PaymentStatus status, String transactionId, Long customerId, Long postNum, Integer pageNo, Integer pageSize);
    Payment getPaymentInfo(Long paymentId);
}
