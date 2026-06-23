package com.example.Tour_Recommendation.dto.response.admin;

import com.example.Tour_Recommendation.model.Enum.DashboardPeriod;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardResponse {

    private DashboardStatsResponse stats;

    private DashboardPeriod period;

    @JsonProperty("bookings_chart")
    private List<BookingChartPointResponse> bookingsChart;

    @JsonProperty("recent_bookings")
    private List<RecentBookingResponse> recentBookings;
}
