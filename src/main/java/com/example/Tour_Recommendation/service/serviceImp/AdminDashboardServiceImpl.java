package com.example.Tour_Recommendation.service.serviceImp;

import com.example.Tour_Recommendation.common.ApiResponse;
import com.example.Tour_Recommendation.dto.response.admin.AdminDashboardResponse;
import com.example.Tour_Recommendation.dto.response.admin.BookingChartPointResponse;
import com.example.Tour_Recommendation.dto.response.admin.DashboardStatsResponse;
import com.example.Tour_Recommendation.dto.response.admin.RecentBookingResponse;
import com.example.Tour_Recommendation.model.Enum.BookingStatus;
import com.example.Tour_Recommendation.model.Enum.DashboardPeriod;
import com.example.Tour_Recommendation.model.entity.Booking;
import com.example.Tour_Recommendation.repository.BookingRepository;
import com.example.Tour_Recommendation.repository.TourRepository;
import com.example.Tour_Recommendation.repository.UserRepository;
import com.example.Tour_Recommendation.service.AdminDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminDashboardServiceImpl implements AdminDashboardService {

    private static final ZoneId ZONE = ZoneId.systemDefault();
    private static final int DEFAULT_RECENT_LIMIT = 10;
    private static final int MAX_RECENT_LIMIT = 50;

    private final UserRepository userRepository;
    private final TourRepository tourRepository;
    private final BookingRepository bookingRepository;

    @Override
    public ApiResponse<AdminDashboardResponse> getDashboard(DashboardPeriod period, int recentLimit) {
        DashboardPeriod resolvedPeriod = period != null ? period : DashboardPeriod.THIS_MONTH;
        int resolvedLimit = resolveRecentLimit(recentLimit);

        AdminDashboardResponse dashboard = AdminDashboardResponse.builder()
                .stats(buildStats())
                .period(resolvedPeriod)
                .bookingsChart(buildBookingsChart(resolvedPeriod))
                .recentBookings(buildRecentBookings(resolvedLimit))
                .build();

        return ApiResponse.success("Dashboard fetched successfully", dashboard);
    }

    @Override
    public ApiResponse<DashboardStatsResponse> getStats() {
        return ApiResponse.success("Dashboard stats fetched successfully", buildStats());
    }

    @Override
    public ApiResponse<List<BookingChartPointResponse>> getBookingsChart(DashboardPeriod period) {
        DashboardPeriod resolvedPeriod = period != null ? period : DashboardPeriod.THIS_MONTH;
        return ApiResponse.success(
                "Bookings chart fetched successfully",
                buildBookingsChart(resolvedPeriod)
        );
    }

    @Override
    public ApiResponse<List<RecentBookingResponse>> getRecentBookings(int limit) {
        return ApiResponse.success(
                "Recent bookings fetched successfully",
                buildRecentBookings(resolveRecentLimit(limit))
        );
    }

    private DashboardStatsResponse buildStats() {
        BigDecimal totalRevenue = bookingRepository.sumTotalPriceByStatuses(
                List.of(BookingStatus.CONFIRMED, BookingStatus.COMPLETED)
        );
        if (totalRevenue == null) {
            totalRevenue = BigDecimal.ZERO;
        }

        return DashboardStatsResponse.builder()
                .totalUsers(userRepository.count())
                .totalTours(tourRepository.count())
                .totalBookings(bookingRepository.count())
                .totalRevenue(totalRevenue)
                .build();
    }

    private List<BookingChartPointResponse> buildBookingsChart(DashboardPeriod period) {
        LocalDate endDate = LocalDate.now(ZONE).plusDays(1);
        LocalDate startDate = resolveChartStartDate(period, endDate);

        Instant startInstant = startDate.atStartOfDay(ZONE).toInstant();
        Instant endInstant = endDate.atStartOfDay(ZONE).toInstant();

        Map<LocalDate, Long> countsByDate = new HashMap<>();
        for (Object[] row : bookingRepository.countBookingsGroupedByDate(startInstant, endInstant)) {
            LocalDate date = toLocalDate(row[0]);
            long count = ((Number) row[1]).longValue();
            countsByDate.put(date, count);
        }

        List<BookingChartPointResponse> chart = new ArrayList<>();
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            chart.add(BookingChartPointResponse.builder()
                    .date(date)
                    .bookingCount(countsByDate.getOrDefault(date, 0L))
                    .build());
        }
        return chart;
    }

    private List<RecentBookingResponse> buildRecentBookings(int limit) {
        List<Booking> bookings = bookingRepository
                .findAllByOrderByCreatedAtDesc(PageRequest.of(0, limit))
                .getContent();

        return bookings.stream()
                .map(RecentBookingResponse::from)
                .toList();
    }

    private LocalDate resolveChartStartDate(DashboardPeriod period, LocalDate endDate) {
        return switch (period) {
            case LAST_7_DAYS -> endDate.minusDays(7);
            case LAST_30_DAYS -> endDate.minusDays(30);
            case THIS_MONTH -> endDate.minusDays(1).withDayOfMonth(1);
        };
    }

    private int resolveRecentLimit(int limit) {
        if (limit <= 0) {
            return DEFAULT_RECENT_LIMIT;
        }
        return Math.min(limit, MAX_RECENT_LIMIT);
    }

    private LocalDate toLocalDate(Object value) {
        if (value instanceof LocalDate localDate) {
            return localDate;
        }
        if (value instanceof Date sqlDate) {
            return sqlDate.toLocalDate();
        }
        if (value instanceof Instant instant) {
            return instant.atZone(ZONE).toLocalDate();
        }
        if (value instanceof java.util.Date utilDate) {
            return utilDate.toInstant().atZone(ZONE).toLocalDate();
        }
        throw new IllegalStateException("Unsupported date type: " + value.getClass().getName());
    }
}
