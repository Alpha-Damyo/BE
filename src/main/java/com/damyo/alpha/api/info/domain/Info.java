package com.damyo.alpha.api.info.domain;

import com.damyo.alpha.api.smokingarea.domain.SmokingArea;
import com.damyo.alpha.api.user.domain.User;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @Column(name = "score")
    private Integer score;

    @Column(name = "opened")
    private Boolean opened;

    @Column(name = "closed")
    private Boolean closed;

    @Column(name = "indoor")
    private Boolean indoor;

    @Column(name = "outdoor")
    private Boolean outdoor;
}
