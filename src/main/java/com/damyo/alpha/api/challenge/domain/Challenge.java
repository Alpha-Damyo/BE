package com.damyo.alpha.api.challenge.domain;

import com.damyo.alpha.api.challenge.controller.dto.ChallengeResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Challenge {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "banner_img_url")
    private String bannerImgUrl;

    @Column(name = "detail_img_url")
    private String detailImgUrl;

    public ChallengeResponse toDto() {
        return new ChallengeResponse(name, startTime, endTime, bannerImgUrl, detailImgUrl);
    }
}
