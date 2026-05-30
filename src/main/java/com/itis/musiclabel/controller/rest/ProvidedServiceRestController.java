package com.itis.musiclabel.controller.rest;

import com.itis.musiclabel.model.ProvidedService;
import com.itis.musiclabel.service.ProvidedServiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@Tag(name = "Services", description = "API for managing label services")
public class ProvidedServiceRestController {

    private final ProvidedServiceService providedServiceService;

    public ProvidedServiceRestController(ProvidedServiceService providedServiceService) {
        this.providedServiceService = providedServiceService;
    }

    @GetMapping
    @Operation(summary = "Get all services")
    public ResponseEntity<List<ProvidedService>> getAll() {
        return ResponseEntity.ok(providedServiceService.getAllServices());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get service by ID")
    public ResponseEntity<ProvidedService> getById(@PathVariable Long id) {
        ProvidedService service = providedServiceService.getServiceById(id);
        if (service == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service);
    }

    @GetMapping("/label/{labelId}")
    @Operation(summary = "Get services by label ID")
    public ResponseEntity<List<ProvidedService>> getByLabel(@PathVariable Long labelId) {
        return ResponseEntity.ok(providedServiceService.getServicesByLabel(labelId));
    }

    @PostMapping
    @Operation(summary = "Create a new service")
    public ResponseEntity<String> create(@RequestParam String name,
                                         @RequestParam String description,
                                         @RequestParam(defaultValue = "0") double basePrice,
                                         @RequestParam Long labelId) {
        providedServiceService.createService(labelId, name, description, basePrice);
        return ResponseEntity.status(HttpStatus.CREATED).body("Service created");
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing service")
    public ResponseEntity<String> update(@PathVariable Long id,
                                         @RequestParam String name,
                                         @RequestParam String description,
                                         @RequestParam(defaultValue = "0") double basePrice) {
        providedServiceService.updateService(id, name, description, basePrice);
        return ResponseEntity.ok("Service updated");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a service")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        providedServiceService.deleteService(id);
        return ResponseEntity.ok("Service deleted");
    }
}