package com.example.Tour_Recommendation.service;

import com.example.Tour_Recommendation.common.ApiResponse;
import com.example.Tour_Recommendation.dto.response.admin.AdminDashboardResponse;
import com.example.Tour_Recommendation.dto.response.admin.BookingChartPointResponse;
import com.example.Tour_Recommendation.dto.response.admin.DashboardStatsResponse;
import com.example.Tour_Recommendation.dto.response.admin.RecentBookingResponse;
import com.example.Tour_Recommendation.model.Enum.DashboardPeriod;

import java.util.List;

public interface AdminDashboardService {

    ApiResponse<AdminDashboardResponse> getDashboard(DashboardPeriod period, int recentLimit);

    ApiResponse<DashboardStatsResponse> getStats();

    ApiResponse<List<BookingChartPointResponse>> getBookingsChart(DashboardPeriod period);

    ApiResponse<List<RecentBookingResponse>> getRecentBookings(int limit);
}
