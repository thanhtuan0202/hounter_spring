package com.hounter.backend.application.controllers;

import com.hounter.backend.application.DTO.PaymentDTO.CreatePaymentDTO;
import com.hounter.backend.business_logic.interfaces.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/callback")
    public ResponseEntity<?> callback(
            @RequestParam("vnp_TmnCode") String vnp_TmnCode,
            @RequestParam("vnp_ResponseCode") String vnp_ResponseCode
    ){
//        String reactAppUrl = "https://youtube.com";
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Location", reactAppUrl);
//        return new ResponseEntity<>(headers, HttpStatus.FOUND);
        if(vnp_ResponseCode.equals("00")){
            return ResponseEntity.ok("Giao dich thanh cong.");
        }
        return ResponseEntity.ok("Giao dich that bai.");
    }

    @GetMapping("/detail")
    public ResponseEntity<?> detail(@RequestHeader(name = "X-FORWARDED-FOR", required = false) String xForwardedFor,
                                    @RequestHeader(name = "Remote-Addr", required = false) String remoteAddr,
                                    @RequestParam("id") String id,
                                    @RequestParam("trans-date") String transDate) throws IOException {
        this.paymentService.getPaymentInfo(id, transDate,xForwardedFor, remoteAddr);
        return ResponseEntity.ok("Ok");
    }
    @PostMapping("create-payment")
    public ResponseEntity<?> createPayment(@RequestHeader(name = "X-FORWARDED-FOR", required = false) String xForwardedFor,
                                           @RequestHeader(name = "Remote-Addr", required = false) String remoteAddr) throws IOException {
        CreatePaymentDTO createPaymentDTO = this.paymentService.createPaymentOfPost(xForwardedFor, remoteAddr);

        return ResponseEntity.ok(createPaymentDTO);
    }

}
