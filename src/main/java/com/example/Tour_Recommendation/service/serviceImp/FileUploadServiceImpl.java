package com.example.Tour_Recommendation.service.serviceImp;

import com.example.Tour_Recommendation.common.ApiResponse;
import com.example.Tour_Recommendation.dto.response.UploadResponse;
import com.example.Tour_Recommendation.model.Enum.ImageUploadType;
import com.example.Tour_Recommendation.service.FileStorageService;
import com.example.Tour_Recommendation.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    private final FileStorageService fileStorageService;

    @Override
    public ApiResponse<UploadResponse> uploadImage(MultipartFile file, ImageUploadType type) {
        ImageUploadType uploadType = type != null ? type : ImageUploadType.IMAGE;
        String imageUrl = fileStorageService.store(file, uploadType.getFolder());

        UploadResponse response = UploadResponse.builder()
                .imageUrl(imageUrl)
                .build();

        return ApiResponse.success("Image uploaded successfully", response);
    }
}
