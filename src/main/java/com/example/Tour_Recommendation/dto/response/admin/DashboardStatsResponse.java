package com.example.Tour_Recommendation.dto.response.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsResponse {

    @JsonProperty("total_users")
    private long totalUsers;

    @JsonProperty("total_tours")
    private long totalTours;

    @JsonProperty("total_bookings")
    private long totalBookings;

    @JsonProperty("total_revenue")
    private BigDecimal totalRevenue;
}
