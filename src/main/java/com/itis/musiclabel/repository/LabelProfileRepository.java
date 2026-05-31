package com.itis.musiclabel.repository;

import com.itis.musiclabel.model.LabelProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LabelProfileRepository extends JpaRepository<LabelProfile, Long> {


    Optional<LabelProfile> findByUserId(Long userId);


    @Query("SELECT lp.id FROM LabelProfile lp WHERE lp.user.id = :userId")
    Optional<Long> findProfileIdByUserId(@Param("userId") Long userId);


}