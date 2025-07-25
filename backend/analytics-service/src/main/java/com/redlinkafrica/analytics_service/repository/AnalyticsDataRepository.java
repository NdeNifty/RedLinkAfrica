package com.redlinkafrica.analytics_service.repository;

import com.redlinkafrica.analytics_service.model.AnalyticsData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.time.LocalDateTime;

public interface AnalyticsDataRepository extends JpaRepository<AnalyticsData, Long> {
    List<AnalyticsData> findByMetricAndTimestampBetween(String metric, LocalDateTime start, LocalDateTime end);
    List<AnalyticsData> findByEntityId(String entityId);
}