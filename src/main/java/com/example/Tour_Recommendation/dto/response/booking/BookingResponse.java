package com.example.Tour_Recommendation.dto.response.booking;

import com.example.Tour_Recommendation.model.Enum.BookingStatus;
import com.example.Tour_Recommendation.model.entity.Booking;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {

    private Long id;

    @JsonProperty("booking_code")
    private String bookingCode;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("tour_id")
    private Long tourId;

    @JsonProperty("tour_title")
    private String tourTitle;

    @JsonProperty("tour_date")
    private LocalDate tourDate;

    @JsonProperty("people_count")
    private Integer peopleCount;

    @JsonProperty("total_price")
    private BigDecimal totalPrice;

    private BookingStatus status;

    @JsonProperty("created_at")
    private Instant createdAt;

    @JsonProperty("updated_at")
    private Instant updatedAt;

    public static BookingResponse from(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .bookingCode(booking.getBookingCode())
                .userId(booking.getUser().getId())
                .userName(booking.getUser().getFullName())
                .tourId(booking.getTour().getId())
                .tourTitle(booking.getTour().getTitle())
                .tourDate(booking.getTourDate())
                .peopleCount(booking.getPeopleCount())
                .totalPrice(booking.getTotalPrice())
                .status(booking.getStatus())
                .createdAt(booking.getCreatedAt())
                .updatedAt(booking.getUpdatedAt())
                .build();
    }
}
