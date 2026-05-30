package com.itis.musiclabel.repository;

import com.itis.musiclabel.model.ProvidedService;

import java.util.List;

public interface ProvidedServiceRepositoryCustom {
    List<ProvidedService> findServicesWithFilters(String name, Long labelId, String sortBy);
}