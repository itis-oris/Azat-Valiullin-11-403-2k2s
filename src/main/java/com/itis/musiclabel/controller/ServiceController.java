package com.itis.musiclabel.controller;

import com.itis.musiclabel.model.ProvidedService;
import com.itis.musiclabel.model.User;
import com.itis.musiclabel.service.ProvidedServiceService;
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
@RequestMapping("/service")
public class ServiceController {

    private final ProvidedServiceService providedServiceService;
    private final UserService userService;

    public ServiceController(ProvidedServiceService providedServiceService, UserService userService) {
        this.providedServiceService = providedServiceService;
        this.userService = userService;
    }

    @GetMapping("/manage")
    public String manage(Model model) {
        User user = userService.getCurrentUser();
        Long labelId = providedServiceService.getLabelProfileId(user.getId());

        model.addAttribute("user", user);
        model.addAttribute("services", providedServiceService.getServicesByLabel(labelId));
        model.addAttribute("currencyService", providedServiceService);

        return "label/services";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
        model.addAttribute("service", new ProvidedService());
        model.addAttribute("isEdit", false);

        return "label/service-form";
    }

    @PostMapping("/create")
    public String create(@RequestParam String name,
                         @RequestParam String description,
                         @RequestParam(defaultValue = "0") double basePrice,
                         RedirectAttributes redirectAttributes) {

        User user = userService.getCurrentUser();
        Long labelId = providedServiceService.getLabelProfileId(user.getId());

        providedServiceService.createService(labelId, name, description, basePrice);
        redirectAttributes.addAttribute("success", "1");

        return "redirect:/service/manage";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        ProvidedService service = providedServiceService.getServiceById(id);

        model.addAttribute("user", userService.getCurrentUser());
        model.addAttribute("service", service);
        model.addAttribute("isEdit", true);

        return "label/service-form";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       @RequestParam String name,
                       @RequestParam String description,
                       @RequestParam(defaultValue = "0") double basePrice,
                       RedirectAttributes redirectAttributes) {

        providedServiceService.updateService(id, name, description, basePrice);
        redirectAttributes.addAttribute("success", "1");

        return "redirect:/service/manage";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        providedServiceService.deleteService(id);
        redirectAttributes.addAttribute("success", "1");

        return "redirect:/service/manage";
    }

    @GetMapping("/browse")
    public String browse(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
        model.addAttribute("services", providedServiceService.getAllServices());
        model.addAttribute("currencyService", providedServiceService);

        return "label/services";
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) String name,
                         @RequestParam(required = false) Long labelId,
                         @RequestParam(defaultValue = "name") String sortBy,
                         Model model) {
        model.addAttribute("user", userService.getCurrentUser());
        model.addAttribute("services", providedServiceService.searchServices(name, labelId, sortBy));
        return "label/services";
    }
}