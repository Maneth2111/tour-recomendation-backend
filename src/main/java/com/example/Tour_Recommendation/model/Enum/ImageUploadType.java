package com.example.Tour_Recommendation.model.Enum;

public enum ImageUploadType {
        AVATAR("avatars"),
        TOUR("tours"),
        IMAGE("images");

    private final String folder;

    ImageUploadType(String folder) {
        this.folder = folder;
    }

    public String getFolder() {
        return folder;
    }
}
