package com.damyo.alpha.api.smokingarea.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SmokingAreaReportDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "other_suggestion", length = 500)
    private String otherSuggestion;

    @Column(name = "smoking_area_id")
    private String smokingAreaId;

}

