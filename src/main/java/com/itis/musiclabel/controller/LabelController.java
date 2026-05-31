package com.itis.musiclabel.controller;

import com.itis.musiclabel.model.LabelProfile;
import com.itis.musiclabel.model.User;
import com.itis.musiclabel.service.ProvidedServiceService;
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
@RequestMapping("/label")
public class LabelController {

    private final SubmissionService submissionService;
    private final ProvidedServiceService providedServiceService;
    private final UserService userService;

    public LabelController(SubmissionService submissionService,
                           ProvidedServiceService providedServiceService,
                           UserService userService) {
        this.submissionService = submissionService;
        this.providedServiceService = providedServiceService;
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        User user = userService.getCurrentUser();

        Long labelId;
        try {
            labelId = providedServiceService.getLabelProfileId(user.getId());
        } catch (RuntimeException e) {
            return "redirect:/label/profile";
        }

        model.addAttribute("user", user);

        if (labelId != null) {
            model.addAttribute("pendingSubmissionsCount",
                    submissionService.getPendingSubmissionsCountByLabel(labelId));
            model.addAttribute("totalSubmissionsCount",
                    submissionService.getTotalSubmissionsCountByLabel(labelId));
            model.addAttribute("servicesCount",
                    providedServiceService.getServicesCountByLabel(labelId));
            model.addAttribute("approvalRate",
                    Math.round(submissionService.getApprovalRateByLabel(labelId)));
            model.addAttribute("recentSubmissions",
                    submissionService.getRecentSubmissionsByLabel(labelId, 5));
            model.addAttribute("labelServices",
                    providedServiceService.getServicesByLabel(labelId, 3));
        }
        model.addAttribute("currencyService", providedServiceService);
        return "label/dashboard";
    }

    @GetMapping("/profile")
    public String profile(Model model,
                          @RequestParam(value = "success", required = false) String success,
                          @RequestParam(value = "error", required = false) String error) {

        User user = userService.getCurrentUser();
        LabelProfile profile = userService.getLabelProfile(user.getId());

        model.addAttribute("user", user);
        model.addAttribute("profile", profile);

        if (success != null) {
            model.addAttribute("successMessage", "Profile saved successfully!");
        }
        if (error != null) {
            model.addAttribute("errorMessage", "Error saving profile. Please try again.");
        }

        return "label/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@RequestParam String labelName,
                                @RequestParam(required = false) String description,
                                @RequestParam(required = false) String contactEmail,
                                @RequestParam(required = false) String website,
                                RedirectAttributes redirectAttributes) {

        User user = userService.getCurrentUser();

        if (labelName == null || labelName.trim().isEmpty()) {
            redirectAttributes.addAttribute("error", "1");
            return "redirect:/label/profile";
        }

        try {
            LabelProfile profile = userService.getLabelProfile(user.getId());

            if (profile == null) {
                profile = new LabelProfile();
                profile.setUser(user);
            }

            profile.setLabelName(labelName.trim());
            profile.setDescription(description != null ? description.trim() : null);
            profile.setContactEmail(contactEmail != null ? contactEmail.trim() : null);
            profile.setWebsite(website != null ? website.trim() : null);

            boolean success = userService.saveLabelProfile(profile);

            if (success) {
                redirectAttributes.addAttribute("success", "1");
            } else {
                redirectAttributes.addAttribute("error", "1");
            }
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", "1");
        }

        return "redirect:/label/profile";
    }

    @GetMapping({"", "/", "/home"})
    public String home() {
        return "redirect:/label/dashboard";
    }

    @GetMapping("/stats")
    public String stats(Model model) {
        User user = userService.getCurrentUser();
        Long labelId = providedServiceService.getLabelProfileId(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("stats", providedServiceService.getLabelStats(labelId));
        model.addAttribute("currencyService", providedServiceService);
        return "label/dashboard";
    }
}