package com.co_notice.controllers;

import com.co_notice.services.COService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class COController {

    @Autowired
    private COService coService;

    @GetMapping("/notices")
    public String getNotices(){
        coService.processPendingTriggers();
        return "success";
    }
}
