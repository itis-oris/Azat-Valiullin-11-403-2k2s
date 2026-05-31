package com.itis.musiclabel.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "submissions")
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    private ArtistProfile artist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private ProvidedService service;

    @Column(name = "track_title", nullable = false, length = 200)
    private String trackTitle;

    @Column(name = "track_file_url", length = 500)
    private String trackFileUrl;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private SubmissionStatus status = SubmissionStatus.PENDING;

    @Column(name = "submission_date")
    private LocalDateTime submissionDate;

    @Column(name = "label_comment", columnDefinition = "TEXT")
    private String labelComment;

    @Column(name = "administered_date")
    private LocalDateTime administeredDate;

    @Transient
    private String artistName;

    @Transient
    private String serviceName;

    @Transient
    private String labelName;

    public Submission() {}

    public Submission(ArtistProfile artist, ProvidedService service,
                      String trackTitle, String trackFileUrl) {
        this.artist = artist;
        this.service = service;
        this.trackTitle = trackTitle;
        this.trackFileUrl = trackFileUrl;
        this.status = SubmissionStatus.PENDING;
        this.submissionDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArtistProfile getArtist() {
        return artist;
    }

    public void setArtist(ArtistProfile artist) {
        this.artist = artist;
    }

    public ProvidedService getService() {
        return service;
    }

    public void setService(ProvidedService service) {
        this.service = service;
    }

    public String getTrackTitle() {
        return trackTitle;
    }

    public void setTrackTitle(String trackTitle) {
        this.trackTitle = trackTitle;
    }

    public String getTrackFileUrl() {
        return trackFileUrl;
    }

    public void setTrackFileUrl(String trackFileUrl) {
        this.trackFileUrl = trackFileUrl;
    }

    public SubmissionStatus getStatus() {
        return status;
    }

    public void setStatus(SubmissionStatus status) {
        this.status = status;
    }

    public LocalDateTime getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getLabelComment() {
        return labelComment;
    }

    public void setLabelComment(String labelComment) {
        this.labelComment = labelComment;
    }

    public LocalDateTime getAdministeredDate() {
        return administeredDate;
    }

    public void setAdministeredDate(LocalDateTime administeredDate) {
        this.administeredDate = administeredDate;
    }

    public String getArtistName() {
        if (artistName == null && artist != null) {
            return artist.getArtistName();
        }
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getServiceName() {
        if (serviceName == null && service != null) {
            return service.getName();
        }
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getLabelName() {
        if (labelName == null && service != null && service.getLabel() != null) {
            return service.getLabel().getLabelName();
        }
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getFormattedSubmissionDate() {
        if (submissionDate == null) return "Unknown date";
        return submissionDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' HH:mm"));
    }

    public String getShortSubmissionDate() {
        if (submissionDate == null) return "Unknown date";
        return submissionDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
    }

    public String getIsoSubmissionDate() {
        if (submissionDate == null) return "";
        return submissionDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public String getFormattedAdministeredDate() {
        if (administeredDate == null) return "Not reviewed";
        return administeredDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' HH:mm"));
    }

}