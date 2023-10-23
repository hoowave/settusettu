package com.project.settusettu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @GetMapping("/login")
    public String loginForm() {
        return "/auth/loginForm";
    }

    @GetMapping("/join")
    public String joinForm() {
        return "/auth/joinForm";
    }

}
