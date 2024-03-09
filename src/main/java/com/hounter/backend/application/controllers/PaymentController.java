package com.hounter.backend.application.controllers;

import com.hounter.backend.application.DTO.PaymentDTO.CreatePaymentDTO;
import com.hounter.backend.business_logic.interfaces.PaymentService;
import com.hounter.backend.business_logic.services.CustomUserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    private final CustomUserDetailServiceImpl userDetailsService;

    public PaymentController(CustomUserDetailServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/callback")
    public ResponseEntity<?> callback(
            @RequestParam("vnp_ResponseCode") String vnp_ResponseCode,
            @RequestParam("vnp_BankCode") String vnp_BankCode,
            @RequestParam("vnp_TransactionNo") String vnp_TransactionNo,
            @RequestParam("vnp_BankTranNo") String vnp_BankTranNo,
            @RequestParam("vnp_Amount") Integer vnp_Amount,
            @RequestParam("vnp_TxnRef") Long vnp_TxnRef
    ){

        if(vnp_ResponseCode.equals("00")){
            this.paymentService.confirmSuccessPayment(vnp_TxnRef,vnp_TransactionNo,vnp_BankCode,vnp_Amount);
            // String reactAppUrl = "http://localhost:3000/user/posts";
            //HttpHeaders headers = new HttpHeaders();
            //headers.add("Location", reactAppUrl);
            //return new ResponseEntity<>(headers, HttpStatus.FOUND);
            return ResponseEntity.ok("Giao dich thanh cong.");
        }
        return ResponseEntity.ok("Giao dich that bai.");
    }

    @GetMapping("/detail")
    public ResponseEntity<?> detail(@RequestHeader(name = "X-FORWARDED-FOR", required = false) String xForwardedFor,
                                    @RequestHeader(name = "Remote-Addr", required = false) String remoteAddr,
                                    @RequestParam("id") String id,
                                    @RequestParam("trans-date") String transDate) throws IOException {

        return ResponseEntity.ok(this.paymentService.getPaymentInfo(id, transDate,xForwardedFor, remoteAddr));
    }
    @GetMapping("create-payment")
    public ResponseEntity<?> createPayment(@RequestHeader(name = "X-FORWARDED-FOR", required = false) String xForwardedFor,
                                           @RequestHeader(name = "Remote-Addr", required = false) String remoteAddr,
                                           @RequestParam(name = "postId",required = false) Long postId,
                                           @RequestParam(name = "amount", defaultValue = "0") Long amount) throws IOException {
        Long userId = this.userDetailsService.getCurrentUserDetails().getUserId();
        CreatePaymentDTO createPaymentDTO = this.paymentService.createPaymentOfPost(xForwardedFor, remoteAddr, postId,amount,userId);

        return ResponseEntity.ok(createPaymentDTO);
    }

}
