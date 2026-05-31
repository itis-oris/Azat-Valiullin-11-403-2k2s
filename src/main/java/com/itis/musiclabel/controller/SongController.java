package com.itis.musiclabel.controller;

import com.itis.musiclabel.model.Submission;
import com.itis.musiclabel.model.User;
import com.itis.musiclabel.service.SubmissionService;
import com.itis.musiclabel.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/song")
public class SongController {

    private final SubmissionService submissionService;
    private final UserService userService;

    public SongController(SubmissionService submissionService, UserService userService) {
        this.submissionService = submissionService;
        this.userService = userService;
    }

    @GetMapping("/review/{id}")
    public String reviewForm(@PathVariable Long id, Model model) {
        User user = userService.getCurrentUser();
        Submission submission = submissionService.getSubmissionById(id);

        model.addAttribute("user", user);
        model.addAttribute("submission", submission);

        return "label/review-song";
    }

    @PostMapping("/review/{id}")
    public String review(@PathVariable Long id,
                         @RequestParam String decision,
                         @RequestParam(required = false) String comment,
                         RedirectAttributes redirectAttributes) {

        try {
            if ("approve".equals(decision)) {
                submissionService.approveSubmission(id, comment);
            } else {
                submissionService.rejectSubmission(id, comment);
            }
            redirectAttributes.addAttribute("success", "1");
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", "1");
        }

        return "redirect:/submission/pending";
    }
}