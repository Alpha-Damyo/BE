package com.damyo.alpha.api.report.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findReportByUserIdAndSmokingAreaId(UUID userId, String smokingAreaId);
}
