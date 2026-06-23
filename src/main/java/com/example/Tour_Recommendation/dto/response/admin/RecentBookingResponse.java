package com.example.Tour_Recommendation.dto.response.admin;

import com.example.Tour_Recommendation.model.Enum.BookingStatus;
import com.example.Tour_Recommendation.model.entity.Booking;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecentBookingResponse {

    private Long id;

    @JsonProperty("tour_title")
    private String tourTitle;

    @JsonProperty("tour_image")
    private String tourImage;

    @JsonProperty("tour_date")
    private LocalDate tourDate;

    @JsonProperty("people_count")
    private Integer peopleCount;

    @JsonProperty("total_price")
    private BigDecimal totalPrice;

    private BookingStatus status;

    public static RecentBookingResponse from(Booking booking) {
        return RecentBookingResponse.builder()
                .id(booking.getId())
                .tourTitle(booking.getTour().getTitle())
                .tourImage(booking.getTour().getImageCover())
                .tourDate(booking.getTourDate())
                .peopleCount(booking.getPeopleCount())
                .totalPrice(booking.getTotalPrice())
                .status(booking.getStatus())
                .build();
    }
}
