package com.hounter.backend.business_logic.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String message;
    private String sendAt;
    private String senderUsername;
    private String receiverUsername;
}
