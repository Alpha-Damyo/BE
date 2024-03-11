package com.damyo.alpha.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Info {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "smoking_area_id")
    private Long smokingAreaId;

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
}
