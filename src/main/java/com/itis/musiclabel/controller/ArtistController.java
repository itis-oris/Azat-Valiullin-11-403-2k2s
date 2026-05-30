package com.itis.musiclabel.controller;

import com.itis.musiclabel.model.ArtistProfile;
import com.itis.musiclabel.model.User;
import com.itis.musiclabel.service.SubmissionService;
import com.itis.musiclabel.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/artist")
public class ArtistController {

    private final SubmissionService submissionService;
    private final UserService userService;

    public ArtistController(SubmissionService submissionService, UserService userService) {
        this.submissionService = submissionService;
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        User user = userService.getCurrentUser();
        Long artistId = submissionService.getArtistProfileId(user.getId());

        model.addAttribute("user", user);

        if (artistId != null) {
            model.addAttribute("submissionsCount", submissionService.getSubmissionsCountByArtist(artistId));
            model.addAttribute("pendingSubmissions", submissionService.getPendingSubmissionsCountByArtist(artistId));
            model.addAttribute("approvedSubmissions", submissionService.getApprovedSubmissionsCountByArtist(artistId));
            model.addAttribute("recentSubmissions", submissionService.getRecentSubmissionsByArtist(artistId, 5));
        }

        return "artist/dashboard";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        User user = userService.getCurrentUser();
        ArtistProfile profile = userService.getArtistProfile(user.getId());

        model.addAttribute("user", user);
        model.addAttribute("profile", profile);

        return "artist/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@RequestParam String artistName,
                                @RequestParam String genre,
                                @RequestParam String description,
                                RedirectAttributes redirectAttributes) {

        User user = userService.getCurrentUser();

        ArtistProfile profile = userService.getArtistProfile(user.getId());
        if (profile == null) {
            profile = new ArtistProfile();
            profile.setUser(user);
        }

        profile.setArtistName(artistName);
        profile.setGenre(genre);
        profile.setDescription(description);

        boolean success = userService.saveArtistProfile(profile);

        if (success) {
            redirectAttributes.addAttribute("success", "1");
        } else {
            redirectAttributes.addAttribute("error", "1");
        }

        return "redirect:/artist/profile";
    }
}