package com.hounter.backend.application.controllers;

import com.hounter.backend.business_logic.entities.Notify;
import com.hounter.backend.business_logic.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;


    @PostMapping()
    public ResponseEntity<?> addNotification(){
        try{
            Notify notify = new Notify("subject", "directObject", "content", "createAt", true, 52);
            this.notificationService.createNotification(notify);
            return ResponseEntity.ok("Notification created successfully!");
        }
        catch (Exception e){
            return ResponseEntity.status(500).body("Something went wrong!");
        }
    }
}
