package com.damyo.alpha.api.user.domain;

import com.damyo.alpha.api.info.domain.Info;
import com.damyo.alpha.api.picture.domain.Picture;
import com.damyo.alpha.api.smokingdata.domain.SmokingData;
import com.damyo.alpha.api.star.domain.Star;
import com.damyo.alpha.api.auth.controller.dto.SignUpRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "profile_url")
    private String profileUrl;
    @Column(name = "contribution")
    private int contribution;
    @Column(name = "gender")
    private String gender;
    @Column(name = "age")
    private int age;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Picture> pictures = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<SmokingData> smokingDatas = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Star> starList = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Info> infoList = new ArrayList<>();

    public User(SignUpRequest signUpDto, String profileUrl) {
        this(null, signUpDto.name(), signUpDto.email(), null, profileUrl, 0, signUpDto.gender(), signUpDto.age(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }
}
