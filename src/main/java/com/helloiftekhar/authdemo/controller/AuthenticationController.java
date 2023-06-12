package com.helloiftekhar.authdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/register")
    public String register() {
        return "register";
    }
}
