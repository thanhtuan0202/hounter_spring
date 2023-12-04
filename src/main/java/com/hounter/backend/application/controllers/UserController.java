package com.hounter.backend.application.controllers;

import com.hounter.backend.application.DTO.CustomerDTO.CustomerResponseDTO;
import com.hounter.backend.application.DTO.CustomerDTO.UpdateInfoDTO;
import com.hounter.backend.application.DTO.PostDto.ShortPostResponse;
import com.hounter.backend.business_logic.interfaces.CustomerService;
import com.hounter.backend.business_logic.services.CustomUserDetailServiceImpl;
import com.hounter.backend.shared.exceptions.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<?> getUserInfo(){
        try{
            Long user_id = this.userDetailsService.getCurrentUserDetails().getUserId();
            CustomerResponseDTO response = this.userService.getCustomerInfo(user_id);
            return ResponseEntity.ok(response);
        }
        catch (Exception e){
            return new ResponseEntity<String>("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getPostOfUser(
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(value = "sortBy", defaultValue = "create_at") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
            @RequestParam(value = "status") String status
    ){
        try {
            Long userId = this.userDetailsService.getCurrentUserDetails().getUserId();
            List<ShortPostResponse> response = this.userService.getPostOfCustomer(pageSize, pageNo,sortBy,sortDir,status,userId);
            if(response == null){
                return ResponseEntity.noContent().build();
            }
            else{
                return ResponseEntity.ok(response);
            }
        } catch (NotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping("/payments")
    public ResponseEntity<?> getPaymentList(){
        return null;
    }

    @GetMapping("/balances")
    public ResponseEntity<?> getBalanceHistory(){
        return null;
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> changeUserInfo(@PathVariable("id") Long id, @RequestBody UpdateInfoDTO userInfoDTO){
        CustomerResponseDTO result = userService.changeCustomerInfo(id, userInfoDTO);
        return null;
    }
}
