package com.itis.musiclabel.repository;

import com.itis.musiclabel.model.ArtistProfile;
import com.itis.musiclabel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistProfileRepository extends JpaRepository<ArtistProfile, Long> {


    Optional<ArtistProfile> findByUser(User user);


    Optional<ArtistProfile> findByUserId(Long userId);


    @Query("SELECT ap.id FROM ArtistProfile ap WHERE ap.user.id = :userId")
    Optional<Long> findProfileIdByUserId(@Param("userId") Long userId);
}