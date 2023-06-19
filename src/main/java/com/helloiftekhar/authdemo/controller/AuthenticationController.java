package com.helloiftekhar.authdemo.controller;

import com.helloiftekhar.authdemo.model.Role;
import com.helloiftekhar.authdemo.model.User;
import com.helloiftekhar.authdemo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AuthenticationController {

    @Autowired
    UserService userService;


    @GetMapping("/login")
    public String login() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
        return "redirect:/";
    }


    @GetMapping("/register")
    public String register(ModelMap model) {
        User user = new User();
        List<Role> roleList = userService.getAllRoles();

        model.put("roleList", roleList);
        model.put("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (userService.save(user)) {
            redirectAttributes.addFlashAttribute("SUCCESS_MESSAGE", "User successfully registered");
            return "redirect:/login";
        }

        redirectAttributes.addFlashAttribute("ERROR_MESSAGE", "User with this email already exist! Try new one");

        return "redirect:/register";
    }

}
