package com.itis.musiclabel.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class TrackInfoService {

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    private static final String API_URL = "https://itunes.apple.com/search";

    public Map<String, Object> searchTrack(String trackName) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("success", true);

        try {
            String url = API_URL + "?term=" + java.net.URLEncoder.encode(trackName, "UTF-8") + "&limit=1&entity=musicTrack";

            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String json = response.body().string();
                    System.out.println("iTunes response: " + json);
                    JsonNode root = mapper.readTree(json);

                    if (root.has("results") && root.get("results").size() > 0) {
                        JsonNode track = root.get("results").get(0);

                        result.put("found", true);
                        result.put("title", safeText(track, "trackName"));
                        result.put("artist", safeText(track, "artistName"));
                        result.put("album", safeText(track, "collectionName"));
                        result.put("genre", safeText(track, "primaryGenreName"));
                        result.put("year", formatDate(safeText(track, "releaseDate")));
                        result.put("duration", formatDuration(track));
                        result.put("price", "$" + safeText(track, "trackPrice"));
                        result.put("thumbnail", safeText(track, "artworkUrl100"));
                        result.put("previewUrl", safeText(track, "previewUrl"));
                        result.put("country", safeText(track, "country"));

                        return result;
                    }

                    result.put("found", false);
                    result.put("message", "Track not found in iTunes database");
                    return result;
                }
            }
        } catch (IOException e) {
            System.err.println("iTunes API error: " + e.getMessage());
        }

        result.put("found", false);
        result.put("message", "API unavailable. Try again later.");
        return result;
    }

    private String safeText(JsonNode node, String field) {
        return node.has(field) && !node.get(field).isNull() ? node.get(field).asText() : "N/A";
    }

    private String formatDate(String date) {
        if (date.length() >= 10) {
            return date.substring(0, 10);
        }
        return date;
    }

    private String formatDuration(JsonNode track) {
        if (track.has("trackTimeMillis")) {
            long ms = track.get("trackTimeMillis").asLong();
            long sec = ms / 1000;
            return String.format("%d:%02d", sec / 60, sec % 60);
        }
        return "N/A";
    }
}