package com.hounter.backend.business_logic.services;

import com.hounter.backend.application.DTO.AdminDTO.CreateStaffDTO;
import com.hounter.backend.application.DTO.AdminDTO.CustomerRestDTO;
import com.hounter.backend.application.DTO.AdminDTO.PaymentResAdminDTO;
import com.hounter.backend.application.DTO.AdminDTO.StaffResDTO;
import com.hounter.backend.business_logic.entities.Customer;
import com.hounter.backend.business_logic.entities.Payment;
import com.hounter.backend.business_logic.entities.Post;
import com.hounter.backend.business_logic.entities.Staff;
import com.hounter.backend.business_logic.interfaces.AccountRoleService;
import com.hounter.backend.business_logic.interfaces.AdminService;
import com.hounter.backend.business_logic.mapper.AdminMapping;
import com.hounter.backend.business_logic.mapper.CustomerMapping;
import com.hounter.backend.data_access.repositories.CustomerRepository;
import com.hounter.backend.data_access.repositories.PaymentRepository;
import com.hounter.backend.data_access.repositories.PostRepository;
import com.hounter.backend.data_access.repositories.StaffRepository;
import com.hounter.backend.shared.enums.Status;
import com.hounter.backend.shared.exceptions.NotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
import java.time.format.DateTimeFormatter;
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
    private PostRepository postRepository;
    @Autowired
    private AccountRoleService accountRoleService;
    @Autowired
    private PaymentRepository paymentRepository;
    @PersistenceContext
    protected EntityManager em;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<CustomerRestDTO> getListCustomer(Integer pageNo, Integer pageSize) {
        Pageable pageable =  PageRequest.of(pageNo, pageSize);
        Page<Customer> customers = this.customerRepository.findAll(pageable);
        log.info("Customer return " + customers.getSize());
        List<Customer> customerLst = customers.stream().toList();
        List<CustomerRestDTO> response = new ArrayList<CustomerRestDTO>();
        for(Customer customer : customerLst){
            response.add(CustomerMapping.adminMapping(customer));
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
        return CustomerMapping.adminMapping(customer);
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
                    staff.getPhoneNumber(), staff.getEmail(), staff.getAddress(), staff.getStartDate(), staff.getIsActive()));
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
        return new StaffResDTO(staff.getId(), staff.getFull_name(), staff.getUsername(),
                staff.getPhoneNumber(), staff.getEmail(), staff.getAddress(), staff.getStartDate(), staff.getIsActive());
    }

    @Override
    public boolean deleteStaff(Long staffId){
        Optional<Staff> optionalStaff = this.staffRepository.findById(staffId);
        if(optionalStaff.isEmpty()){
            throw new NotFoundException("Staff not found.", HttpStatus.OK);
        }
        Staff staff = optionalStaff.get();
        if(!staff.getIsActive()){
            return false;
        }
        staff.setIsActive(false);
        this.staffRepository.save(staff);
        return true;
    }
    @Override
    public boolean updatePostStatus(Long postId, Status status){
        Optional<Post> optionalPost = this.postRepository.findById(postId);
        if(optionalPost.isEmpty()){
            throw new NotFoundException("Post not found.", HttpStatus.OK);
        }
        Post post = optionalPost.get();
        if(post.getStatus().equals(Status.waiting) && status.equals(Status.active)){
            post.setStatus(status);
            post.setUpdateAt(LocalDate.now());
        }
        return true;
    }
    @Override
    public List<PaymentResAdminDTO> getPaymentsAdmin(String fromDate, String toDate, Status status, String transactionId, 
                                                        Long customerId, Long postNum, Integer pageNo, Integer pageSize){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Payment> cq = cb.createQuery(Payment.class);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Root<Payment> paymenRoot = cq.from(Payment.class);
        List<Predicate> predicates = new ArrayList<>();
        if(fromDate != null){
            LocalDate from = LocalDate.parse(fromDate, formatter);
            predicates.add(cb.greaterThanOrEqualTo(paymenRoot.get("createAt"), from));
        }
        if(toDate != null){
            LocalDate to = LocalDate.parse(toDate, formatter);
            predicates.add(cb.lessThanOrEqualTo(paymenRoot.get("createAt"), to));
        }
        if(status != null){
            predicates.add(cb.equal(paymenRoot.get("status"), status));
        }
        if(transactionId != null){
            predicates.add(cb.equal(paymenRoot.get("paymentId"), transactionId));
        }
        if(customerId != null){

            predicates.add(cb.equal(paymenRoot.get("customer").get("id"), customerId));
        }
        if (postNum != null) {
            predicates.add(cb.equal(paymenRoot.get("postNum"), postNum));
        }
        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.desc(paymenRoot.get("createAt")));
        List<Payment> paymentList = em.createQuery(cq).setMaxResults(pageSize)
                .setFirstResult(pageNo * pageSize)
                .getResultList();
        if(paymentList != null){
            List<PaymentResAdminDTO> response = new ArrayList<PaymentResAdminDTO>();
            for(Payment payment : paymentList){
                response.add(AdminMapping.mappingPayment(payment));
            }
            return response;
        }
        return null;
    }

    @Override
    public Payment getPaymentInfo(Long paymentId) {
        Payment payment = this.paymentRepository.findByPostNum(paymentId);
        if(payment == null){
            throw new NotFoundException("Payment not found.", HttpStatus.OK);
        }
        return payment;
    }
}
