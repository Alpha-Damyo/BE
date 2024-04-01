package com.damyo.alpha.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Info {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "smoking_area_Id")
    @ToString.Exclude
    private SmokingArea smokingArea;

    @Column(name = "score")
    private Integer score;

    @Column(name = "opened")
    private Boolean opened;

    @Column(name = "closed")
    private Boolean closed;

    @Column(name = "not_exist")
    private Boolean notExist;

    @Column(name = "air_out")
    private Boolean airOut;

    @Column(name = "hygiene")
    private Boolean hygiene;

    @Column(name = "dirty")
    private Boolean dirty;

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
}
