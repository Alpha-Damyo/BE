package com.damyo.alpha.domain;

import com.damyo.alpha.dto.response.SmokingAreaResponse;
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
    private BigDecimal latitude;

    @Column(name = "longitude")
    private BigDecimal longitude;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "status", nullable = false)
    private boolean status;

    @Column(name = "address", nullable = false, length = 100)
    private String address;

    @Column(name = "description", length = 200)
    private String description;

    @Column(name = "score")
    private Float score;

    @Column(name = "opened")
    private boolean opened;

    @Column(name = "closed")
    private boolean closed;

    @Column(name = "hygiene")
    private boolean hygiene;

    @Column(name = "dirty")
    private boolean dirty;

    @Column(name = "air_out")
    private boolean airOut;

    @Column(name = "no_exist")
    private boolean noExist;

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

    public SmokingAreaResponse toDTO(){
        return new SmokingAreaResponse(id, name, latitude, longitude,
                address, createdAt, status, description, score, opened,
                closed, hygiene, dirty, airOut, noExist);
    }
}
