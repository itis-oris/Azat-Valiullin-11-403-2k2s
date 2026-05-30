package com.itis.musiclabel.service;

import com.itis.musiclabel.model.ArtistProfile;
import com.itis.musiclabel.model.Song;
import com.itis.musiclabel.repository.ArtistProfileRepository;
import com.itis.musiclabel.repository.SongRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SongService {

    private final SongRepository songRepository;
    private final ArtistProfileRepository artistProfileRepository;

    public SongService(SongRepository songRepository,
                       ArtistProfileRepository artistProfileRepository) {
        this.songRepository = songRepository;
        this.artistProfileRepository = artistProfileRepository;
    }

    @Transactional(readOnly = true)
    public List<Song> getSongsByArtist(Long artistId) {
        return songRepository.findByArtistIdOrderByUploadedAtDesc(artistId);
    }

    public void uploadSong(Long artistId, String title, String genre,
                           String fileUrl, Integer duration, Long fileSize) {
        ArtistProfile artist = artistProfileRepository.findById(artistId)
                .orElseThrow(() -> new RuntimeException("Artist not found"));
        Song song = new Song(artist, title, genre, fileUrl, duration, fileSize);
        songRepository.save(song);
    }

    public void approveSong(Long songId) {
        songRepository.approveSong(songId);
    }

    @Transactional(readOnly = true)
    public List<Song> getPendingSongs() {
        return songRepository.findByIsApprovedFalseOrderByUploadedAtDesc();
    }
}