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
    private int score;

    @Column(name = "opened")
    private boolean opened;

    @Column(name = "closed")
    private boolean closed;

    @Column(name = "not_exist")
    private boolean notExist;

    @Column(name = "air_out")
    private boolean airOut;

    @Column(name = "hygiene")
    private boolean hygiene;

    @Column(name = "dirty")
    private boolean dirty;

    @Column(name = "indoor")
    private boolean indoor;

    @Column(name = "outdoor")
    private boolean outdoor;

    @Column(name = "big")
    private boolean big;

    @Column(name = "small")
    private boolean small;

    @Column(name = "crowded")
    private boolean crowded;

    @Column(name = "quite")
    private boolean quite;

    @Column(name = "chair")
    private boolean chair;
}
