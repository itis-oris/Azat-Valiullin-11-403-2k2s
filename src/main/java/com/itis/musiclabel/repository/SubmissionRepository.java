package com.itis.musiclabel.repository;

import com.itis.musiclabel.model.Submission;
import com.itis.musiclabel.model.SubmissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {


    @Query("SELECT s FROM Submission s " +
            "JOIN FETCH s.artist ap " +
            "JOIN FETCH s.service sv " +
            "JOIN FETCH sv.label lp " +
            "WHERE s.artist.id = :artistId " +
            "ORDER BY s.submissionDate DESC")
    List<Submission> findByArtistIdWithDetails(@Param("artistId") Long artistId);


    @Query("SELECT s FROM Submission s " +
            "JOIN FETCH s.artist ap " +
            "JOIN FETCH s.service sv " +
            "JOIN FETCH sv.label lp " +
            "WHERE sv.label.id = :labelId AND s.status = :status " +
            "ORDER BY s.submissionDate DESC")
    List<Submission> findByLabelIdAndStatusWithDetails(@Param("labelId") Long labelId,
                                                       @Param("status") SubmissionStatus status);


    @Query("SELECT s FROM Submission s " +
            "JOIN FETCH s.artist ap " +
            "JOIN FETCH s.service sv " +
            "JOIN FETCH sv.label lp " +
            "WHERE s.id = :id")
    Optional<Submission> findByIdWithDetails(@Param("id") Long id);


    @Modifying
    @Transactional
    @Query("UPDATE Submission s SET s.status = :status, s.labelComment = :comment, " +
            "s.administeredDate = CURRENT_TIMESTAMP WHERE s.id = :id")
    void updateStatus(@Param("id") Long id,
                      @Param("status") SubmissionStatus status,
                      @Param("comment") String comment);


    long countByArtistId(Long artistId);


    long countByArtistIdAndStatus(Long artistId, SubmissionStatus status);

    @Query("SELECT s FROM Submission s " +
            "JOIN FETCH s.artist ap " +
            "JOIN FETCH s.service sv " +
            "JOIN FETCH sv.label lp " +
            "WHERE sv.label.id = :labelId " +
            "ORDER BY s.submissionDate DESC")
    List<Submission> findByLabelIdWithDetails(@Param("labelId") Long labelId);
}