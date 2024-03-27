package com.hounter.backend.application.controllers;

import com.hounter.backend.business_logic.entities.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void processMessageFromClient(@Payload Message message) throws Exception {
        System.out.println(message);
        messagingTemplate.convertAndSendToUser(String.valueOf(message.getReceiverUsername()),"", message);
    }

}
