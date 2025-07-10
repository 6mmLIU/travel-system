package com.example.travelsystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class ErrorPageController {

    /**
     * 处理 403（权限不足）错误：
     * 1. 返回 auth/error.html 模板
     * 2. 响应状态码仍然是 403
     */
    @GetMapping("/auth/error")
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String accessDenied(Model model) {
        model.addAttribute("status",403);
        return "auth/error";  // 对应 resources/templates/auth/error.html
    }

}
