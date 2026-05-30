package com.itis.musiclabel.controller;

import com.itis.musiclabel.model.User;
import com.itis.musiclabel.service.ProvidedServiceService;
import com.itis.musiclabel.service.SubmissionService;
import com.itis.musiclabel.service.UserService;
import com.itis.musiclabel.util.FileStorage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/submission")
public class SubmissionController {

    String uploadPath = System.getProperty("user.dir") + "/src/main/resources/static/uploads";
    private final SubmissionService submissionService;
    private final ProvidedServiceService providedServiceService;
    private final UserService userService;
    private final FileStorage fileStorage;

    public SubmissionController(SubmissionService submissionService,
                                ProvidedServiceService providedServiceService,
                                UserService userService,
                                FileStorage fileStorage) {
        this.submissionService = submissionService;
        this.providedServiceService = providedServiceService;
        this.userService = userService;
        this.fileStorage = fileStorage;
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
        model.addAttribute("labels", providedServiceService.getAllLabels());
        model.addAttribute("allServices", providedServiceService.getAllServices());
        return "artist/submission-form";
    }

    @PostMapping("/create")
    public String create(@RequestParam String trackTitle,
                         @RequestParam Long serviceId,
                         @RequestParam("trackFile") MultipartFile trackFile,
                         RedirectAttributes redirectAttributes,
                         Model model) {

        User user = userService.getCurrentUser();

        if (trackFile.isEmpty()) {
            model.addAttribute("error", "Please select a file");
            model.addAttribute("user", user);
            model.addAttribute("labels", providedServiceService.getAllLabels());
            model.addAttribute("trackTitle", trackTitle);
            return "artist/submission-form";
        }

        if (!fileStorage.isValidAudioFile(trackFile)) {
            model.addAttribute("error", "Only MP3 and WAV files are allowed");
            model.addAttribute("user", user);
            model.addAttribute("labels", providedServiceService.getAllLabels());
            model.addAttribute("trackTitle", trackTitle);
            return "artist/submission-form";
        }

        if (!fileStorage.isValidFileSize(trackFile, 10 * 1024 * 1024)) {
            model.addAttribute("error", "File size must be less than 10MB");
            model.addAttribute("user", user);
            model.addAttribute("labels", providedServiceService.getAllLabels());
            model.addAttribute("trackTitle", trackTitle);
            return "artist/submission-form";
        }

        try {
            String fileName = fileStorage.saveFile(trackFile, uploadPath);
            Long artistId = submissionService.getArtistProfileId(user.getId());
            submissionService.createSubmission(artistId, serviceId, trackTitle.trim(), fileName);
            redirectAttributes.addAttribute("success", "1");
            return "redirect:/submission/my";
        } catch (Exception e) {
            model.addAttribute("error", "Error uploading file: " + e.getMessage());
            model.addAttribute("user", user);
            model.addAttribute("labels", providedServiceService.getAllLabels());
            model.addAttribute("trackTitle", trackTitle);
            return "artist/submission-form";
        }
    }

    @GetMapping("/my")
    public String mySubmissions(Model model) {
        User user = userService.getCurrentUser();
        Long artistId = submissionService.getArtistProfileId(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("submissions", submissionService.getSubmissionsByArtist(artistId));
        return "artist/my-submissions";
    }

    @GetMapping("/pending")
    public String pending(Model model) {
        User user = userService.getCurrentUser();
        Long labelId = providedServiceService.getLabelProfileId(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("submissions", submissionService.getPendingSubmissionsByLabel(labelId));
        return "label/pending-submissions";
    }

    @GetMapping("/history")
    public String history(Model model) {
        User user = userService.getCurrentUser();
        Long labelId = providedServiceService.getLabelProfileId(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("submissions", submissionService.getAllSubmissionsByLabel(labelId));
        return "label/all-submissions";
    }
}