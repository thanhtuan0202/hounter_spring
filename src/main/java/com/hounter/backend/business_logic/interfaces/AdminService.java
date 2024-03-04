package com.hounter.backend.business_logic.interfaces;

import com.hounter.backend.application.DTO.AdminDTO.CreateStaffDTO;
import com.hounter.backend.application.DTO.AdminDTO.CustomerRestDTO;
import com.hounter.backend.application.DTO.AdminDTO.PaymentResAdminDTO;
import com.hounter.backend.application.DTO.AdminDTO.StaffResDTO;
import com.hounter.backend.business_logic.entities.Payment;
import com.hounter.backend.shared.enums.Status;

import java.util.List;

public interface AdminService {
    public List<CustomerRestDTO> getListCustomer(Integer pageNo, Integer pageSize);
    public CustomerRestDTO getCustomerInfo(Long customerId);
    public boolean createStaff(CreateStaffDTO createStaffDTO);
    public List<StaffResDTO> getListStaff(Integer pageNo, Integer pageSize);
    public StaffResDTO getStaffInfo(Long staffId);
    public boolean deleteStaff(Long staffId);
    public boolean updatePostStatus(Long postId, Status status);
    public List<PaymentResAdminDTO> getPaymentsAdmin(String fromDate, String toDate, Status status, String transactionId, Long customerId, Long postNum, Integer pageNo, Integer pageSize);
    public Payment getPaymentInfo(Long paymentId);
}
