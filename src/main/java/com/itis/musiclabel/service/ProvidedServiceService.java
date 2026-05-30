package com.itis.musiclabel.service;

import com.itis.musiclabel.model.LabelProfile;
import com.itis.musiclabel.model.ProvidedService;
import com.itis.musiclabel.repository.LabelProfileRepository;
import com.itis.musiclabel.repository.ProvidedServiceRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProvidedServiceService {

    private final ProvidedServiceRepository providedServiceRepository;
    private final LabelProfileRepository labelProfileRepository;
    private final CurrencyService currencyService;

    public ProvidedServiceService(ProvidedServiceRepository providedServiceRepository,
                                  LabelProfileRepository labelProfileRepository, CurrencyService currencyService) {
        this.providedServiceRepository = providedServiceRepository;
        this.labelProfileRepository = labelProfileRepository;
        this.currencyService = currencyService;
    }

    @Transactional(readOnly = true)
    public Long getLabelProfileId(Long userId) {
        return labelProfileRepository.findProfileIdByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Label profile not found for user: " + userId));
    }

    @Transactional(readOnly = true)
    public List<ProvidedService> getServicesByLabel(Long labelId) {
        return providedServiceRepository.findByLabelIdOrderByName(labelId);
    }
    @Cacheable(value = "servicesByLabel", key = "#labelId")
    @Transactional(readOnly = true)
    public List<ProvidedService> getServicesByLabel(Long labelId, int limit) {
        List<ProvidedService> services = providedServiceRepository.findByLabelIdOrderByName(labelId);
        return services.stream().limit(limit).toList();
    }
    @Cacheable(value = "allServices", key = "'all'")
    @Transactional(readOnly = true)
    public List<ProvidedService> getAllServices() {
        return providedServiceRepository.findAllWithLabelNames();
    }

    @Transactional(readOnly = true)
    public List<LabelProfile> getAllLabels() {
        try {
            return labelProfileRepository.findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    @CacheEvict(value = {"allServices", "servicesByLabel"}, allEntries = true)
    public void createService(Long labelId, String name, String description, double price) {
        LabelProfile label = labelProfileRepository.findById(labelId)
                .orElseThrow(() -> new RuntimeException("Label not found"));
        ProvidedService service = new ProvidedService(label, name, description,
                BigDecimal.valueOf(price));
        providedServiceRepository.save(service);
    }
    @CacheEvict(value = {"allServices", "servicesByLabel"}, allEntries = true)
    public void updateService(Long serviceId, String name, String description, double price) {
        ProvidedService service = providedServiceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));
        service.setName(name);
        service.setDescription(description);
        service.setBasePrice(BigDecimal.valueOf(price));
        providedServiceRepository.save(service);
    }
    @CacheEvict(value = {"allServices", "servicesByLabel"}, allEntries = true)
    public void deleteService(Long serviceId) {
        providedServiceRepository.deleteById(serviceId);
    }

    @Transactional(readOnly = true)
    public int getServicesCountByLabel(Long labelId) {
        return providedServiceRepository.findByLabelIdOrderByName(labelId).size();
    }

    @Transactional(readOnly = true)
    public ProvidedService getServiceById(Long serviceId) {
        return providedServiceRepository.findById(serviceId).orElse(null);
    }

    @Transactional(readOnly = true)
    public Object getLabelStats(Long labelId) {
        return new Object() {
            public final int totalServices = getServicesCountByLabel(labelId);
            public final int activeSubmissions = 10;
            public final double revenue = 1500.0;
        };
    }

    @Transactional(readOnly = true)
    public List<ProvidedService> searchServices(String name, Long labelId, String sortBy) {
        return providedServiceRepository.findServicesWithFilters(name, labelId, sortBy);
    }

    public String getFormattedPrice(ProvidedService service) {
        if (service.getBasePrice().compareTo(BigDecimal.ZERO) == 0) {
            return "Free";
        }
        BigDecimal rubPrice = currencyService.convertUsdToRub(service.getBasePrice());
        return "$" + service.getBasePrice() + " / " + rubPrice + " ₽";
    }
}