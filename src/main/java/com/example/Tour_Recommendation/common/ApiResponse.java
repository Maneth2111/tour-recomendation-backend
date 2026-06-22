package com.example.Tour_Recommendation.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private int status;
    private Instant timestamp;
    private T data;
    private Map<String, String> errors;

    public static <T> ApiResponse<T> success(String message, T data) {
        return success(HttpStatus.OK.value(), message, data);
    }

    public static <T> ApiResponse<T> success(int status, String message, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .status(status)
                .data(data)
                .timestamp(Instant.now())
                .build();
    }

    public static ApiResponse<Void> error(int status, String message) {
        return error(status, message, null);
    }

    public static ApiResponse<Void> error(int status, String message, Map<String, String> errors) {
        return ApiResponse.<Void>builder()
                .success(false)
                .message(message)
                .status(status)
                .errors(errors)
                .timestamp(Instant.now())
                .build();
    }
}
