package com.itis.musiclabel.repository;

import com.itis.musiclabel.model.ProvidedService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProvidedServiceRepositoryImpl implements ProvidedServiceRepositoryCustom {

    private final EntityManager entityManager;

    public ProvidedServiceRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<ProvidedService> findServicesWithFilters(String name, Long labelId, String sortBy) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProvidedService> query = cb.createQuery(ProvidedService.class);
        Root<ProvidedService> service = query.from(ProvidedService.class);

        List<Predicate> predicates = new ArrayList<>();


        if (name != null && !name.isEmpty()) {
            predicates.add(cb.like(cb.lower(service.get("name")), "%" + name.toLowerCase() + "%"));
        }


        if (labelId != null) {
            predicates.add(cb.equal(service.get("label").get("id"), labelId));
        }

        query.where(predicates.toArray(new Predicate[0]));


        if ("price".equals(sortBy)) {
            query.orderBy(cb.asc(service.get("basePrice")));
        } else {
            query.orderBy(cb.asc(service.get("name")));
        }

        TypedQuery<ProvidedService> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
}