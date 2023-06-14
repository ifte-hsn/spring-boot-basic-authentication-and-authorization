package com.helloiftekhar.authdemo.controller;

import com.helloiftekhar.authdemo.model.Role;
import com.helloiftekhar.authdemo.model.User;
import com.helloiftekhar.authdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
        return "login";
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
    public String saveUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        if (userService.save(user)) {
            redirectAttributes.addFlashAttribute("SUCCESS_MESSAGE", "User successfully registered");
            return "redirect:/login";
        }

        redirectAttributes.addFlashAttribute("ERROR_MESSAGE", "User with this email already exist! Try new one");

        return "redirect:/register";
    }

}
