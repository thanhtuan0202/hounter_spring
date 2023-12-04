package com.hounter.backend.application.controllers;

import com.hounter.backend.application.DTO.AccountDTO.AccountResponse;
import com.hounter.backend.application.DTO.AccountDTO.LoginDTO;
import com.hounter.backend.application.DTO.AccountDTO.RegisterDTO;
import com.hounter.backend.business_logic.interfaces.AccountService;
import com.hounter.backend.shared.binding.BindingBadRequest;
import com.hounter.backend.shared.utils.MappingError;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO, BindingResult binding) throws Exception {
        if (binding.hasErrors()) {
            List<BindingBadRequest> error_lst = MappingError.mappingError(binding);
            return ResponseEntity.badRequest().body(error_lst);
        }
        try {
            String res = this.accountService.login(loginDTO);
            return new ResponseEntity<String>(res, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO registerDTO, BindingResult binding) {
        if (binding.hasErrors()) {
            List<BindingBadRequest> error_lst = new ArrayList<>();
            for (FieldError field : binding.getFieldErrors()) {
                BindingBadRequest badRequest = new BindingBadRequest(field.getField(), field.getDefaultMessage());
                error_lst.add(badRequest);
            }
            return ResponseEntity.badRequest().body(error_lst);
        }
        try {
            AccountResponse response = this.accountService.register(registerDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
