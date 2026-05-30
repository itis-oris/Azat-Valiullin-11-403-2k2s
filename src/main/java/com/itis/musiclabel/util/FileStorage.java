package com.itis.musiclabel.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileStorage {

    public String saveFile(MultipartFile file, String uploadPath) throws IOException {
        String fileName = generateFileName(file);
        Path filePath = Paths.get(uploadPath, fileName);

        Files.createDirectories(filePath.getParent());
        file.transferTo(filePath.toFile());

        return fileName;
    }

    public boolean deleteFile(String filePath) {
        try {
            return Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            return false;
        }
    }

    private String generateFileName(MultipartFile file) {
        String originalName = file.getOriginalFilename();
        String extension = "";

        if (originalName != null && originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf("."));
        }

        return UUID.randomUUID().toString() + extension;
    }

    public boolean isValidAudioFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null &&
                (contentType.equals("audio/mpeg") ||
                        contentType.equals("audio/wav") ||
                        contentType.equals("audio/mp3"));
    }

    public boolean isValidFileSize(MultipartFile file, long maxSize) {
        return file.getSize() <= maxSize;
    }
}