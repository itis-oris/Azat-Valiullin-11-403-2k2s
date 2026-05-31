package com.itis.musiclabel.repository;

import com.itis.musiclabel.model.ProvidedService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProvidedServiceRepository extends JpaRepository<ProvidedService, Long>, ProvidedServiceRepositoryCustom {


    List<ProvidedService> findByLabelIdOrderByName(Long labelId);


    @Query("SELECT ps FROM ProvidedService ps " +
            "JOIN FETCH ps.label lp " +
            "ORDER BY lp.labelName, ps.name")
    List<ProvidedService> findAllWithLabelNames();


    @Query("SELECT ps FROM ProvidedService ps " +
            "WHERE ps.id IN (SELECT s.service.id FROM Submission s)")
    List<ProvidedService> findServicesWithSubmissions();

}