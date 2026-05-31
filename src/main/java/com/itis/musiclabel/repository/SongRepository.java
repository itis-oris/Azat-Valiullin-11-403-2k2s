package com.itis.musiclabel.repository;

import com.itis.musiclabel.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {


    List<Song> findByArtistIdOrderByUploadedAtDesc(Long artistId);


    List<Song> findByIsApprovedFalseOrderByUploadedAtDesc();


    @Modifying
    @Transactional
    @Query("UPDATE Song s SET s.isApproved = true, s.approvedAt = CURRENT_TIMESTAMP WHERE s.id = :songId")
    void approveSong(@Param("songId") Long songId);

}