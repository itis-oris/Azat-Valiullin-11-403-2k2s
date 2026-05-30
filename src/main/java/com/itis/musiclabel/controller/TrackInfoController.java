package com.itis.musiclabel.controller;

import com.itis.musiclabel.service.TrackInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/track")
public class TrackInfoController {

    private final TrackInfoService trackInfoService;

    public TrackInfoController(TrackInfoService trackInfoService) {
        this.trackInfoService = trackInfoService;
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> info(@RequestParam String title) {
        return ResponseEntity.ok(trackInfoService.searchTrack(title));
    }
}