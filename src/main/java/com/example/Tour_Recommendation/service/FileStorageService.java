package com.example.Tour_Recommendation.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String store(MultipartFile file, String folder);
}
