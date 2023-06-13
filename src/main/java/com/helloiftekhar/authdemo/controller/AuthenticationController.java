package com.helloiftekhar.authdemo.controller;

import com.helloiftekhar.authdemo.model.PasswordResetToken;
import com.helloiftekhar.authdemo.model.User;
import com.helloiftekhar.authdemo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
public class AuthenticationController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return "forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String resetPassword(Model model,
                                @RequestParam("username") String userEmail, RedirectAttributes redirectAttributes) {
        User user = (User) userService.loadUserByUsername(userEmail);

        if (user == null) {
            redirectAttributes.addFlashAttribute("ERROR_MESSAGE", "User does not exist!");
            return "redirect:/forgotPassword";
        }

        String token = UUID.randomUUID().toString();
        createPasswordResetTokenForUser(user, token);


        return "forgotPassword";

    }

    private void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setUser(user);
        resetToken.setToken(token);
        userService.savePasswordResetToken(resetToken);
    }

}
