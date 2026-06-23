package com.example.Tour_Recommendation.service;

import com.example.Tour_Recommendation.common.ApiResponse;
import com.example.Tour_Recommendation.dto.response.UploadResponse;
import com.example.Tour_Recommendation.model.Enum.ImageUploadType;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

    ApiResponse<UploadResponse> uploadImage(MultipartFile file, ImageUploadType type);
}
