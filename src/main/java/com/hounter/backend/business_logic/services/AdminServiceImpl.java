package com.hounter.backend.business_logic.services;

import com.hounter.backend.application.DTO.AdminDTO.*;
import com.hounter.backend.application.DTO.PostDto.ShortPostResponse;
import com.hounter.backend.business_logic.entities.Account;
import com.hounter.backend.business_logic.entities.Customer;
import com.hounter.backend.business_logic.entities.Payment;
import com.hounter.backend.business_logic.entities.Post;
import com.hounter.backend.business_logic.entities.Staff;
import com.hounter.backend.business_logic.interfaces.*;
import com.hounter.backend.business_logic.mapper.AdminMapping;
import com.hounter.backend.business_logic.mapper.CustomerMapping;
import com.hounter.backend.data_access.repositories.AccountRepository;
import com.hounter.backend.data_access.repositories.CustomerRepository;
import com.hounter.backend.data_access.repositories.StaffRepository;
import com.hounter.backend.shared.enums.PaymentStatus;
import com.hounter.backend.shared.enums.Status;
import com.hounter.backend.shared.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private PostService postService;
    @Autowired
    private PostCostService postCostService;
    @Autowired
    private AccountRoleService accountRoleService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<CustomerListResDTO> getListCustomer(Integer pageNo, Integer pageSize) {
        Pageable pageable =  PageRequest.of(pageNo, pageSize);
        Page<Customer> customers = this.customerRepository.findAll(pageable);
        List<Customer> customerLst = customers.stream().toList();
        List<CustomerListResDTO> response = new ArrayList<>();
        for(Customer customer : customerLst){
            response.add(CustomerMapping.adminListMapping(customer));
        }
        return response;
    }

    @Override
    public CustomerRestDTO getCustomerInfo(Long customerId) {
        Optional<Customer> optionalCustomer = this.customerRepository.findById(customerId);
        if(optionalCustomer.isEmpty()){
            throw new NotFoundException("Customer not found.", HttpStatus.OK);
        }
        Customer customer = optionalCustomer.get();
        CustomerRestDTO response =  CustomerMapping.adminMapping(customer);
        List<ShortPostResponse> postList = this.postService.filterPostForUser(5, 0, customer, "", "", "", "", null);
        response.setPostList(postList);
        return response;
    }

    @Transactional(rollbackFor = {SQLException.class})
    @Override
    public boolean createStaff(CreateStaffDTO createStaffDTO) {
        String hashPassword = this.passwordEncoder.encode("password");
        Optional<Staff> optionalStaff = this.staffRepository.findByUsername(createStaffDTO.getUsername());
        if(optionalStaff.isPresent()){
            return false;
        }
        Staff newStaff = new Staff();
        newStaff.setUsername(createStaffDTO.getUsername());
        newStaff.setPassword(hashPassword);
        newStaff.setFull_name(createStaffDTO.getFullName());
        newStaff.setPhoneNumber(createStaffDTO.getPhoneNumber());
        newStaff.setAddress(createStaffDTO.getAddress());
        newStaff.setEmail(createStaffDTO.getEmail());
        newStaff.setStartDate(LocalDate.now());
        newStaff.setCreateAt(LocalDate.now());
        newStaff.setUpdateAt(LocalDate.now());
        newStaff.setBirthDate(createStaffDTO.getDob());
        newStaff.setIsActive(true);
        this.staffRepository.save(newStaff);
        this.accountRoleService.enrollAccountToRole(newStaff.getId(), "STAFF");
        return true;
    }

    @Override
    public List<StaffResDTO> getListStaff(Integer pageNo, Integer pageSize) {
        Pageable pageable =  PageRequest.of(pageNo, pageSize);
        Page<Staff> pageStaff = this.staffRepository.findAll(pageable);
        List<StaffResDTO> response = new ArrayList<StaffResDTO>();
        for(Staff staff : pageStaff){
            response.add(new StaffResDTO(staff.getId(), staff.getFull_name(), staff.getUsername(),
                    staff.getPhoneNumber(), staff.getEmail(), staff.getAddress(),
                    staff.getStartDate(), staff.getIsActive() ? "Hoạt động" : "Không hoạt động"));
        }
        return response;
    }

    @Override
    public StaffResDTO getStaffInfo(Long staffId) {
        Optional<Staff> optionalStaff = this.staffRepository.findById(staffId);
        if(optionalStaff.isEmpty()){
            throw new NotFoundException("Staff not found.", HttpStatus.OK);
        }
        Staff staff = optionalStaff.get();
//        return new StaffResDTO(staff.getId(), staff.getFull_name(), staff.getUsername(),
//                staff.getPhoneNumber(), staff.getEmail(), staff.getAddress(), staff.getStartDate(), staff.getIsActive());
        return null;
    }

    @Override
    public boolean deleteAccount(Long accountId){
        Optional<Account> optionalAccount = this.accountRepository.findById(accountId);
        if(optionalAccount.isEmpty()){
            throw new NotFoundException("Account not found.", HttpStatus.NOT_FOUND);
        }
        Account account = optionalAccount.get();
        if(!account.getIsActive()){
            return false;
        }
        account.setIsActive(false);
        this.accountRepository.save(account);
        return true;
    }

    @Override
    public boolean updatePostStatus(Long postId, Status status){
        Post post = this.postService.findPostById(postId); 
        if(post == null){
            throw new NotFoundException("Post not found.", HttpStatus.OK);
        }
        if(post.getStatus().equals(Status.waiting) && status.equals(Status.active)){
            post.setStatus(status);
            post.setUpdateAt(LocalDate.now());
        }
        return true;
    }

    @Override
    public List<PaymentResAdminDTO> getPaymentsAdmin(String fromDate, String toDate, PaymentStatus status, String transactionId,
                                                     Long customerId, Long postNum, Integer pageNo, Integer pageSize){
        List<Payment> paymentList = this.paymentService.getListPaymentOfCustomer(fromDate, toDate, status, transactionId, customerId, postNum, pageNo, pageSize);
        if(paymentList != null){
            List<PaymentResAdminDTO> response = new ArrayList<>();
            for(Payment payment : paymentList){
                response.add(AdminMapping.mappingPayment(payment));
            }
            return response;
        }
        return null;
    }

    @Override
    public Payment getPaymentInfo(Long postNum) {
        Payment payment = this.paymentService.getPaymentByPostNum(postNum);
        if(payment == null){
            throw new NotFoundException("Payment not found.", HttpStatus.OK);
        }
        return payment;
    }
}
