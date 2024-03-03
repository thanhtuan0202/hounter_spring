package com.hounter.backend.application.controllers;

import com.hounter.backend.business_logic.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public ResponseEntity<?> getNotification(){
        this.notificationService.getDateTest();
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getNotificationByUserId(@PathVariable("userId") Long userId){
        return ResponseEntity.ok("Success");
    }

    @PostMapping()
    public ResponseEntity<?> addNotification(){
        return ResponseEntity.ok("Success");
    }
}
