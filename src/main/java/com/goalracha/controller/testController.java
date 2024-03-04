package com.goalracha.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }
}
