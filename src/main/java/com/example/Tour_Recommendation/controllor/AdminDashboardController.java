package com.example.Tour_Recommendation.controllor;

import com.example.Tour_Recommendation.common.ApiResponse;
import com.example.Tour_Recommendation.dto.response.admin.AdminDashboardResponse;
import com.example.Tour_Recommendation.dto.response.admin.BookingChartPointResponse;
import com.example.Tour_Recommendation.dto.response.admin.DashboardStatsResponse;
import com.example.Tour_Recommendation.dto.response.admin.RecentBookingResponse;
import com.example.Tour_Recommendation.model.Enum.DashboardPeriod;
import com.example.Tour_Recommendation.service.AdminDashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
@Tag(name = "Admin Dashboard")
@SecurityRequirement(name = "Bearer Authentication")
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;

    @GetMapping
    @Operation(summary = "Get full admin dashboard (stats, chart, recent bookings)")
    public ApiResponse<AdminDashboardResponse> getDashboard(
            @RequestParam(defaultValue = "THIS_MONTH") DashboardPeriod period,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return adminDashboardService.getDashboard(period, limit);
    }

    @GetMapping("/stats")
    @Operation(summary = "Get dashboard summary statistics")
    public ApiResponse<DashboardStatsResponse> getStats() {
        return adminDashboardService.getStats();
    }

    @GetMapping("/bookings/chart")
    @Operation(summary = "Get bookings count per day for chart")
    public ApiResponse<List<BookingChartPointResponse>> getBookingsChart(
            @RequestParam(defaultValue = "THIS_MONTH") DashboardPeriod period
    ) {
        return adminDashboardService.getBookingsChart(period);
    }

    @GetMapping("/bookings/recent")
    @Operation(summary = "Get recent bookings for dashboard list")
    public ApiResponse<List<RecentBookingResponse>> getRecentBookings(
            @RequestParam(defaultValue = "10") int limit
    ) {
        return adminDashboardService.getRecentBookings(limit);
    }
}
