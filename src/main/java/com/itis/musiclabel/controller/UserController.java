package com.itis.musiclabel.controller;

import com.itis.musiclabel.model.User;
import com.itis.musiclabel.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String profile() {
        User user = userService.getCurrentUser();

        return switch (user.getRole()) {
            case ARTIST -> "redirect:/artist/profile";
            case LABEL -> "redirect:/label/profile";
        };
    }

    @GetMapping("/settings")
    public String settings() {
        return "redirect:/profile";
    }

    @PostMapping("/update-profile")
    public String updateProfile(@RequestParam String email,
                                @RequestParam String username,
                                RedirectAttributes redirectAttributes) {

        User user = userService.getCurrentUser();

        try {
            userService.updateUserProfile(user.getId(), username, email);
            redirectAttributes.addAttribute("success", "1");
        } catch (RuntimeException e) {
            redirectAttributes.addAttribute("error", "1");
        }

        return "redirect:/user/profile";
    }
}