package com.helloiftekhar.authdemo.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    @GetMapping("/")
    public String index() {
        return "index";
    }


    @GetMapping("admin")
    public String admin() {
        return "admin";
    }


    @GetMapping("accessDenied")
    public String accessDeniedPage() {
        return "accessDenied";
    }
}
