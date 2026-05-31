package com.itis.musiclabel.controller;

import com.itis.musiclabel.model.User;
import com.itis.musiclabel.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String home() {
        try {
            User user = userService.getCurrentUser();
            return switch (user.getRole()) {
                case ARTIST -> "redirect:/artist/dashboard";
                case LABEL -> "redirect:/label/dashboard";
            };
        } catch (Exception e) {
            return "redirect:/auth/login";
        }
    }
}