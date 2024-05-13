package com.hounter.backend.application.controllers;

import com.hounter.backend.application.DTO.PaymentDTO.CreatePaymentDTO;
import com.hounter.backend.business_logic.interfaces.PaymentService;
import com.hounter.backend.business_logic.services.CustomUserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponents;

import java.io.IOException;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    private final CustomUserDetailServiceImpl userDetailsService;

    @Value("${react.url}")
    private String reactAppUrl;

    public PaymentController(CustomUserDetailServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/callback")
    public RedirectView callback(
            @RequestParam(value = "vnp_ResponseCode") String vnp_ResponseCode,
            @RequestParam(value = "vnp_BankCode") String vnp_BankCode,
            @RequestParam(value = "vnp_TransactionNo", required = false) String vnp_TransactionNo,
            @RequestParam(value = "vnp_BankTranNo", required = false) String vnp_BankTranNo,
            @RequestParam(value = "vnp_Amount") Integer vnp_Amount,
            @RequestParam(value = "vnp_TxnRef") String vnp_TxnRef,
            @RequestParam(value = "vnp_PayDate", required = false) String vnp_PayDate
    ){
        UriComponents queryString = this.paymentService.confirmPayment(vnp_ResponseCode, vnp_TxnRef,vnp_TransactionNo,vnp_BankCode,vnp_Amount,vnp_PayDate);
        if(vnp_ResponseCode.equals("00")){
             String reactAppUrl = this.reactAppUrl + "/payment-success" + queryString.toUriString();
            return new RedirectView(reactAppUrl);
        }
        return new RedirectView(this.reactAppUrl + "/payment-failure" + queryString.toUriString());
    }

    @GetMapping("/detail")
    public ResponseEntity<?> detail(@RequestParam("id") String id,
                                    @RequestParam("trans-date") String transDate) throws IOException {

        return ResponseEntity.ok(this.paymentService.getPaymentInfo(id, transDate));
    }
    @GetMapping("create-payment")
    public ResponseEntity<?> createPayment(
                                            @RequestParam(name = "postId",required = false) Long postId,
                                           @RequestParam(name = "amount", defaultValue = "0") Long amount) throws IOException {
        Long userId = this.userDetailsService.getCurrentUserDetails().getUserId();
        CreatePaymentDTO createPaymentDTO = this.paymentService.createPaymentOfPost( postId,amount,userId);
        return ResponseEntity.ok(createPaymentDTO);
    }
}