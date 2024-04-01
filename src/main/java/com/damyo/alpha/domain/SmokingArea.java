package com.damyo.alpha.domain;

import com.damyo.alpha.dto.response.SmokingAreaResponse;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@ToString
@EntityListeners(AuditingEntityListener.class)
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
    private Boolean opened;

    @Column(name = "closed")
    private Boolean closed;

    @Column(name = "hygiene")
    private Boolean hygiene;

    @Column(name = "dirty")
    private Boolean dirty;

    @Column(name = "air_out")
    private Boolean airOut;

    @Column(name = "no_exist")
    private Boolean noExist;

    @Column(name = "indoor")
    private Boolean indoor;

    @Column(name = "outdoor")
    private Boolean outdoor;

    @Column(name = "big")
    private Boolean big;

    @Column(name = "small")
    private Boolean small;

    @Column(name = "crowded")
    private Boolean crowded;

    @Column(name = "quite")
    private Boolean quite;

    @Column(name = "chair")
    private Boolean chair;

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
                closed, hygiene, dirty, airOut, noExist, indoor, outdoor,
                big, small, crowded, quite, chair);
    }
}
