package com.hounter.backend.application.controllers;

import com.hounter.backend.application.DTO.AdminDTO.CreateStaffDTO;
import com.hounter.backend.application.DTO.AdminDTO.CustomerRestDTO;
import com.hounter.backend.application.DTO.AdminDTO.PaymentResAdminDTO;
import com.hounter.backend.application.DTO.AdminDTO.StaffResDTO;
import com.hounter.backend.business_logic.entities.Payment;
import com.hounter.backend.business_logic.interfaces.AdminService;
import com.hounter.backend.business_logic.mapper.AdminMapping;
import com.hounter.backend.shared.enums.Status;
import com.hounter.backend.shared.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @GetMapping("/users")
    public ResponseEntity<?> getUserAccounts(
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo
    ){
        try{
            List<CustomerRestDTO> response = this.adminService.getListCustomer(pageNo - 1, pageSize);
            return ResponseEntity.ok(response);
        }
        catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getUserAccountInfo(
            @PathVariable("userId") Long userId
    ){
        try{
            CustomerRestDTO customer = this.adminService.getCustomerInfo(userId);
            return ResponseEntity.ok(customer);
        }
        catch (NotFoundException e){
            return new ResponseEntity<String>(e.getMessage(), e.getStatus());
        }
        catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/staffs")
    public ResponseEntity<?> getStaffAccounts(
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo
    ){
        try{
            List<StaffResDTO> response = this.adminService.getListStaff(pageNo - 1, pageSize);
            return ResponseEntity.ok(response);
        }
        catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/staffs/{staffId}")
    public ResponseEntity<?> getStaffAccountInfo(
            @PathVariable("staffId") Long staffId
    ){
        try{
            StaffResDTO staffResDTO = this.adminService.getStaffInfo(staffId);
            return ResponseEntity.ok(staffResDTO);
        }
        catch (NotFoundException e){
            return new ResponseEntity<String>(e.getMessage(), e.getStatus());
        }
        catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/payment-history")
    public ResponseEntity<?> getPaymentHistory(
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "status", required = false) Status status,
            @RequestParam(value = "fromDate", required = false) String fromDate,
            @RequestParam(value = "toDate", required = false) String toDate,
            @RequestParam(value = "transactionId", required = false) String transactionId,
            @RequestParam(value = "customerId", required = false) Long customerId,
            @RequestParam(value = "postNum", required = false) Long postNum
    ){
        try{
            List<PaymentResAdminDTO> response = this.adminService.getPaymentsAdmin(fromDate, toDate, status, transactionId, customerId,postNum, pageNo - 1, pageSize);
            return response != null ? ResponseEntity.ok(response) : ResponseEntity.noContent().build();
        }
        catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/payment-history/{paymentId}")
    public ResponseEntity<?> getPaymentHistoryByPost(
            @PathVariable("paymentId") Long paymentId
    ){
        try{
            Payment response = this.adminService.getPaymentInfo(paymentId);
            return response != null ? ResponseEntity.ok(AdminMapping.mappingPayment(response)) : ResponseEntity.noContent().build();
        }
        catch (NotFoundException e){
            return new ResponseEntity<String>(e.getMessage(), e.getStatus());
        }
        catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/staffs")
    public ResponseEntity<?> createStaffAccount(@RequestBody CreateStaffDTO createStaffDTO){
        try{
            boolean isCreated = this.adminService.createStaff(createStaffDTO);
            if(isCreated){
                return ResponseEntity.ok("Staff created successfully.");
            }
            else{
                return ResponseEntity.ok("Duplicate staff username.");
            }
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/posts/{postId}")
    public ResponseEntity<?> updatePostStatus(
            @PathVariable("postId") Long postId,
            @RequestBody Status status
    ){

        return null;
    }
    @DeleteMapping("/staffs/{staffId}")
    public ResponseEntity<?> deleteStaffAccount(@PathVariable("staffId") Long staffId){
        try{
            boolean isDeleted = this.adminService.deleteStaff(staffId);
            if(isDeleted){
                return ResponseEntity.ok("Staff delete successfully.");
            }
            else{
                return ResponseEntity.ok("Something went wrong.");
            }
        }
        catch (NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}