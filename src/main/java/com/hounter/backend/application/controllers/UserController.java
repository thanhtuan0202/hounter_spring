package com.hounter.backend.application.controllers;

import com.hounter.backend.application.DTO.CustomerDTO.CustomerResponseDTO;
import com.hounter.backend.application.DTO.CustomerDTO.UpdateInfoDTO;
import com.hounter.backend.application.DTO.PaymentDTO.PaymentResDTO;
import com.hounter.backend.application.DTO.PostDto.ShortPostResponse;
import com.hounter.backend.business_logic.interfaces.CustomerService;
import com.hounter.backend.business_logic.services.CustomUserDetailServiceImpl;
import com.hounter.backend.shared.binding.BindingBadRequest;
import com.hounter.backend.shared.enums.PaymentStatus;
import com.hounter.backend.shared.exceptions.NotFoundException;
import com.hounter.backend.shared.utils.MappingError;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeParseException;
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
    public ResponseEntity<?> getUserInfo(@PathVariable String userId) {
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
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping("/{userId}/payments")
    public ResponseEntity<?> getPaymentList(@PathVariable("userId") Long userId,
                                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                            @RequestParam(value = "status", required = false) PaymentStatus status,
                                            @RequestParam(value = "fromDate", required = false) String fromDate,
                                            @RequestParam(value = "toDate", required = false) String toDate,
                                            @RequestParam(value = "transactionId", required = false) String transactionId,
                                            @RequestParam(value = "postNum", required = false) Long postNum) {
        try{
            Long tokenId = this.userDetailsService.getCurrentUserDetails().getUserId();
            if(!Objects.equals(tokenId, userId)){
                return new ResponseEntity<>("Forbidden!", HttpStatus.FORBIDDEN);
            }
            List<PaymentResDTO> response = this.userService.getPaymentOfCustomer(fromDate, toDate, status, transactionId, userId, postNum, pageNo - 1, pageSize);
            if (response == null) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(response);
            }
        }
        catch (DateTimeParseException e){
            return new ResponseEntity<>("Invalid date format. Date must be in the format YYYY-MM-DD", HttpStatus.BAD_REQUEST);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
        catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userId}/balances")
    public ResponseEntity<?> getBalanceHistory(@PathVariable("userId") Long userId) {
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
