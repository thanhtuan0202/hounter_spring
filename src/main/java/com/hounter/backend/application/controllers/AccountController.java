package com.hounter.backend.application.controllers;

import com.hounter.backend.application.DTO.AccountDTO.ChangePasswordDTO;
import com.hounter.backend.business_logic.services.AccountServiceImpl;
import com.hounter.backend.business_logic.services.CustomUserDetailServiceImpl;
import com.hounter.backend.shared.binding.BindingBadRequest;
import com.hounter.backend.shared.exceptions.ConfirmPasswordNotMatch;
import com.hounter.backend.shared.utils.MappingError;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountServiceImpl accountService;
    private final CustomUserDetailServiceImpl userDetailsService;

    public AccountController(CustomUserDetailServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    @PostMapping("/{account_id}")
    public ResponseEntity<String> changeActive(@PathVariable("account_id") Long id){
        try{
            boolean result = accountService.changeActive(id);
            if(result){
                return new ResponseEntity<>("Thành công", HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @PatchMapping("/{account_id}")
    public ResponseEntity<?> changePassword(@PathVariable("account_id") Long accountId,@Valid @RequestBody ChangePasswordDTO changePasswordDTO, BindingResult binding) throws Exception {
        if (binding.hasErrors()) {
            List<BindingBadRequest> error_lst = MappingError.mappingError(binding);
            return ResponseEntity.badRequest().body(error_lst);
        }
        Long user_id = this.userDetailsService.getCurrentUserDetails().getUserId();
        try{
            boolean result = accountService.changePassword(changePasswordDTO, user_id);
            if (result){
                return new ResponseEntity<>("Thay đổi mật khẩu thành công!", HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        catch (ConfirmPasswordNotMatch e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

}
