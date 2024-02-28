package com.goalracha.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class jungtaeController {

    @GetMapping("/babo")
    public String hello() {
        return "정태야 나에게도 사정이 있었어";
    }

}
