package com.damyo.alpha.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SmokingArea {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "latitude")
    private Float latitude;

    @Column(name = "longitude")
    private Float longitude;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime created_at;

    @Column(name = "status", nullable = false, length = 5)
    private String status;

    @Column(name = "address", nullable = false, length = 100)
    private String address;

    @Column(name = "description", length = 200)
    private String description;

    @Builder.Default
    @OneToMany(mappedBy = "smoking_area_id", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<SmokingData> smokingDataList = new ArrayList<SmokingData>();

}
