package com.example.Tour_Recommendation.service.serviceImp;

import com.example.Tour_Recommendation.config.FileStorageProperties;
import com.example.Tour_Recommendation.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png", "webp", "gif");
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    private final FileStorageProperties fileStorageProperties;

    @Override
    public String store(MultipartFile file, String folder) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is required");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new RuntimeException("File size must not exceed 5MB");
        }

        String extension = getExtension(file.getOriginalFilename());
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new RuntimeException("Only JPG, PNG, WEBP, and GIF images are allowed");
        }

        String filename = UUID.randomUUID() + "." + extension;

        try {
            Path targetDir = Paths.get(fileStorageProperties.getUploadDir(), folder)
                    .toAbsolutePath()
                    .normalize();
            Files.createDirectories(targetDir);

            Path targetFile = targetDir.resolve(filename);
            file.transferTo(targetFile.toFile());

            return fileStorageProperties.getBaseUrl() + "/" + folder + "/" + filename;
        } catch (IOException ex) {
            throw new RuntimeException("Failed to store file");
        }
    }

    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            throw new RuntimeException("Invalid file name");
        }
        return filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
    }
}
