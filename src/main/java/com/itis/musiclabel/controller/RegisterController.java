package com.itis.musiclabel.controller;

import com.itis.musiclabel.model.User;
import com.itis.musiclabel.model.User.UserRole;
import com.itis.musiclabel.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registerPage(@RequestParam(value = "error", required = false) String error,
                               Model model) {
        if (error != null) {
            model.addAttribute("error", "Registration failed. Username or email may be taken.");
        }
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String role) {

        User user = userService.registerUser(username, email, password, UserRole.valueOf(role));

        if (user != null) {
            return "redirect:/auth/login?registered=1";
        }

        return "redirect:/auth/register?error=1";
    }
}