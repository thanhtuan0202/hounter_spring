package com.hounter.backend.application.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VNPayResDTO {
    private String vnp_ResponseId;
    private String vnp_Command;
    private String vnp_ResponseCode;
    private String vnp_Message;
    private String vnp_TmnCode;
    private String vnp_TxnRef;
    private String vnp_Amount;
    private String vnp_OrderInfo;
    private String vnp_BankCode;
    private String vnp_PayDate;
    private String vnp_TransactionNo;
    private String vnp_TransactionType;
    private String vnp_TransactionStatus;
    private String vnp_SecureHash;
}
