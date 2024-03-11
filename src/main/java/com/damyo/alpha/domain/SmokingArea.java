package com.damyo.alpha.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

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
    private String id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "latitude")
    private Float latitude;

    @Column(name = "longitude")
    private Float longitude;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "status", nullable = false)
    private boolean status;

    @Column(name = "address", nullable = false, length = 100)
    private String address;

    @Column(name = "description", length = 200)
    private String description;

    @Builder.Default
    @OneToMany(mappedBy = "smokingArea", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<SmokingData> smokingDataList = new ArrayList<SmokingData>();

    @Builder.Default
    @OneToMany(mappedBy = "smokingArea", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Picture> pictureList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "smokingArea", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Info> infoList = new ArrayList<>();
}
