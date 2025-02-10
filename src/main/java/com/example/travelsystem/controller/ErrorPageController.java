package com.example.travelsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorPageController {

    @GetMapping("/auth/error")
    public String error403() {
        return "auth/error"; // 对应 resources/templates/auth/error.html
    }
}
