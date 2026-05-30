package com.itis.musiclabel.controller;

import com.itis.musiclabel.model.ArtistProfile;
import com.itis.musiclabel.model.LabelProfile;
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
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String viewProfile(Model model) {
        User user = userService.getCurrentUser();

        model.addAttribute("user", user);
        model.addAttribute("title", "User Profile");

        if (user.getRole() == User.UserRole.ARTIST) {
            ArtistProfile profile = userService.getArtistProfile(user.getId());
            model.addAttribute("artistProfile", profile);
        } else if (user.getRole() == User.UserRole.LABEL) {
            LabelProfile profile = userService.getLabelProfile(user.getId());
            model.addAttribute("labelProfile", profile);
        }

        return "profile";
    }

    @PostMapping
    public String updateProfile(@RequestParam String email,
                                @RequestParam String username,
                                RedirectAttributes redirectAttributes) {

        User user = userService.getCurrentUser();

        if (email == null || email.trim().isEmpty()
                || username == null || username.trim().isEmpty()) {
            redirectAttributes.addAttribute("error", "Email and username are required");
            return "redirect:/profile";
        }

        try {
            userService.updateUserProfile(user.getId(), username.trim(), email.trim());
            redirectAttributes.addAttribute("message", "Profile updated successfully");
        } catch (RuntimeException e) {
            redirectAttributes.addAttribute("error", e.getMessage());
        }

        return "redirect:/profile";
    }
}