package com.hounter.backend.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hounter.backend.business_logic.services.NotifyService;

@RestController
@RequestMapping("/notify")
public class NotifyController {

    @Autowired
    private NotifyService notifyService;

    @PatchMapping("/{id}")
    public ResponseEntity<?> ReadNotify(@PathVariable("id") Long notifyId) {
        return ResponseEntity.ok(notifyService.ReadNotify(notifyId));
    }

    @GetMapping()
    public ResponseEntity<?> ReadAllNotify(@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo) {
        return ResponseEntity.ok(notifyService.getNotifies());
    }
}
