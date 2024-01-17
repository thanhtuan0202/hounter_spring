package com.hounter.backend.application.controllers;

import com.hounter.backend.application.DTO.CustomerDTO.CustomerResponseDTO;
import com.hounter.backend.application.DTO.CustomerDTO.UpdateInfoDTO;
import com.hounter.backend.application.DTO.PostDto.ShortPostResponse;
import com.hounter.backend.business_logic.interfaces.CustomerService;
import com.hounter.backend.business_logic.services.CustomUserDetailServiceImpl;
import com.hounter.backend.shared.binding.BindingBadRequest;
import com.hounter.backend.shared.exceptions.NotFoundException;
import com.hounter.backend.shared.utils.MappingError;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/customers")
public class UserController {
    @Autowired
    private CustomerService userService;
    private final CustomUserDetailServiceImpl userDetailsService;

    public UserController(CustomUserDetailServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping()
    public ResponseEntity<?> getListCustomer(
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "sortBy", defaultValue = "createAt") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir
    ){
        return null;
    }
    @GetMapping("/get_self_information")
    public ResponseEntity<?> getSelfUserInfo() {
        try {
            Long user_id = this.userDetailsService.getCurrentUserDetails().getUserId();
            CustomerResponseDTO response = this.userService.getCustomerInfo(user_id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<String>("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserInfo() {
        try {
            Long user_id = this.userDetailsService.getCurrentUserDetails().getUserId();
            CustomerResponseDTO response = this.userService.getCustomerInfo(user_id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<String>("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userId}/posts")
    public ResponseEntity<?> getPostOfUser(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "status", required = false,defaultValue = "") String status,
            @RequestParam(value = "category", required = false, defaultValue = "Nhà trọ") String category,
            @RequestParam(value = "cost", required = false, defaultValue = "0") Long cost,
            @RequestParam(value = "beginDate", required = false,defaultValue = "") String beginDate,
            @RequestParam(value = "endDate", required = false,defaultValue = "") String endDate) {
        try {
            Long tokenId = this.userDetailsService.getCurrentUserDetails().getUserId();
            if(!Objects.equals(tokenId, userId)){
                return new ResponseEntity<>("Forbidden!", HttpStatus.FORBIDDEN);
            }
            List<ShortPostResponse> response = this.userService.getPostOfCustomer(pageSize, pageNo - 1,category,cost, userId,beginDate,endDate);
            if (response == null) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(response);
            }
        } catch (NotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping("/{userId}/payments")
    public ResponseEntity<?> getPaymentList() {
        return ResponseEntity.ok("PaymentList");
    }

    @GetMapping("/{userId}/balances")
    public ResponseEntity<?> getBalanceHistory() {
        return ResponseEntity.ok("Balance of user");
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> changeCustomerInfo(@Valid @RequestBody UpdateInfoDTO userInfoDTO, BindingResult binding, @PathVariable("userId") Long userId) {
        if (binding.hasErrors()) {
            List<BindingBadRequest> error_lst = MappingError.mappingError(binding);
            return ResponseEntity.badRequest().body(error_lst);
        }
        try {
            Long userId_token = this.userDetailsService.getCurrentUserDetails().getUserId();
            CustomerResponseDTO result = userService.changeCustomerInfo(userId, userInfoDTO);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
