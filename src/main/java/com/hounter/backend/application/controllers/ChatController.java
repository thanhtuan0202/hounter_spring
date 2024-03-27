package com.hounter.backend.application.controllers;

import com.hounter.backend.business_logic.entities.Message;
import com.hounter.backend.business_logic.interfaces.ChatService;
import com.hounter.backend.business_logic.services.CustomUserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;
    private final CustomUserDetailServiceImpl userDetailsService;

    public ChatController(CustomUserDetailServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    @GetMapping("/list")
    public String listMessages() {
        return "List of messages";
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody Message message) {
        try{
//            Long userId = this.userDetailsService.getCurrentUserDetails().getUserId();
            this.chatService.sendNewMessage(message);
            return new ResponseEntity<>("Message sent", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
