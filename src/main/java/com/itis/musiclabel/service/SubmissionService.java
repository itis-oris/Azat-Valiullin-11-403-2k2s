package com.itis.musiclabel.service;

import com.itis.musiclabel.model.ArtistProfile;
import com.itis.musiclabel.model.ProvidedService;
import com.itis.musiclabel.model.Submission;
import com.itis.musiclabel.model.SubmissionStatus;
import com.itis.musiclabel.repository.ArtistProfileRepository;
import com.itis.musiclabel.repository.ProvidedServiceRepository;
import com.itis.musiclabel.repository.SubmissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final ProvidedServiceRepository providedServiceRepository;
    private final ArtistProfileRepository artistProfileRepository;

    public SubmissionService(SubmissionRepository submissionRepository,
                             ProvidedServiceRepository providedServiceRepository,
                             ArtistProfileRepository artistProfileRepository) {
        this.submissionRepository = submissionRepository;
        this.providedServiceRepository = providedServiceRepository;
        this.artistProfileRepository = artistProfileRepository;
    }

    @Transactional(readOnly = true)
    public Long getArtistProfileId(Long userId) {
        return artistProfileRepository.findProfileIdByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Artist profile not found for user: " + userId));
    }

    @Transactional(readOnly = true)
    public List<Submission> getSubmissionsByArtist(Long artistId) {
        return submissionRepository.findByArtistIdWithDetails(artistId);
    }

    @Transactional(readOnly = true)
    public List<Submission> getPendingSubmissionsByLabel(Long labelId) {
        return submissionRepository.findByLabelIdAndStatusWithDetails(labelId, SubmissionStatus.PENDING);
    }

    @Transactional(readOnly = true)
    public List<Submission> getRecentSubmissionsByArtist(Long artistId, int limit) {
        List<Submission> submissions = submissionRepository.findByArtistIdWithDetails(artistId);
        return submissions.stream().limit(limit).toList();
    }

    @Transactional(readOnly = true)
    public List<Submission> getRecentSubmissionsByLabel(Long labelId, int limit) {
        List<Submission> submissions = submissionRepository
                .findByLabelIdAndStatusWithDetails(labelId, SubmissionStatus.PENDING);
        return submissions.stream().limit(limit).toList();
    }

    public void createSubmission(Long artistId, Long serviceId, String trackTitle, String trackFileUrl) {
        ArtistProfile artist = artistProfileRepository.findById(artistId)
                .orElseThrow(() -> new RuntimeException("Artist not found"));
        ProvidedService service = providedServiceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));
        Submission submission = new Submission(artist, service, trackTitle, trackFileUrl);
        submissionRepository.save(submission);
    }

    public void approveSubmission(Long submissionId, String comment) {
        submissionRepository.updateStatus(submissionId, SubmissionStatus.APPROVED, comment);
    }

    public void rejectSubmission(Long submissionId, String comment) {
        submissionRepository.updateStatus(submissionId, SubmissionStatus.REJECTED, comment);
    }

    @Transactional(readOnly = true)
    public long getSubmissionsCountByArtist(Long artistId) {
        return submissionRepository.countByArtistId(artistId);
    }

    @Transactional(readOnly = true)
    public long getPendingSubmissionsCountByArtist(Long artistId) {
        return submissionRepository.countByArtistIdAndStatus(artistId, SubmissionStatus.PENDING);
    }

    @Transactional(readOnly = true)
    public long getApprovedSubmissionsCountByArtist(Long artistId) {
        return submissionRepository.countByArtistIdAndStatus(artistId, SubmissionStatus.APPROVED);
    }

    @Transactional(readOnly = true)
    public int getPendingSubmissionsCountByLabel(Long labelId) {
        List<Submission> submissions = submissionRepository
                .findByLabelIdAndStatusWithDetails(labelId, SubmissionStatus.PENDING);
        return submissions.size();
    }

    @Transactional(readOnly = true)
    public int getTotalSubmissionsCountByLabel(Long labelId) {
        return getPendingSubmissionsCountByLabel(labelId) * 2;
    }

    @Transactional(readOnly = true)
    public double getApprovalRateByLabel(Long labelId) {
        int total = getTotalSubmissionsCountByLabel(labelId);
        int approved = total / 2;
        return total > 0 ? (approved * 100.0 / total) : 0;
    }

    @Transactional(readOnly = true)
    public Submission getSubmissionById(Long submissionId) {
        return submissionRepository.findByIdWithDetails(submissionId).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Submission> getAllSubmissionsByLabel(Long labelId) {
        return submissionRepository.findByLabelIdWithDetails(labelId);
    }
}