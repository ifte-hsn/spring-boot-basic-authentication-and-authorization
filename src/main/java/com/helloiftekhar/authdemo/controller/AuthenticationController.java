package com.helloiftekhar.authdemo.controller;

import com.helloiftekhar.authdemo.model.Role;
import com.helloiftekhar.authdemo.model.User;
import com.helloiftekhar.authdemo.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

@Controller
public class AuthenticationController {

    @Autowired
    UserService userService;

    @Autowired
    JavaMailSender mailSender;

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

    @GetMapping("/forgotPassword")
    public String showForgotPasswordForm() {
        return "forgetPasswordForm";
    }

    @PostMapping("/forgotPassword")
    public String processForgotPassword(HttpServletRequest request, ModelMap model, RedirectAttributes redirectAttributes) {
        String email = request.getParameter("email");
        String token = UUID.randomUUID().toString();

        try {
            userService.updateResetPasswordToken(token, email);
            String resetPasswordLink = ServletUriComponentsBuilder.fromCurrentContextPath().build().toString() + "/resetPassword?token=" + token;
            sendEmail(email, resetPasswordLink);
            model.addAttribute("SUCCESS_MESSAGE", "We have sent a reset password link to your email. Please check.");
        } catch (EntityNotFoundException e) {
            model.addAttribute("ERROR_MESSAGE", "User with this email not found!.");
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("ERROR_MESSAGE", "Error while sending email");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "forgetPasswordForm";
    }

    public void sendEmail(String email, String sendLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("contact@helloIftekhar.com", "Hello Iftekhar Support");
        helper.setTo(email);

        String subject = "Password Reset";
        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + sendLink + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }

    @GetMapping("/resetPassword")
    public String showResetPasswordForm() {
        return null;
    }

    @PostMapping("/resetPassword")
    public String processResetPassword() {
        return null;
    }


}
