package com.goalracha.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class adminController {
    @GetMapping("/admin2")
    public String admin() {
        return "admin page2";
    }
    
}
