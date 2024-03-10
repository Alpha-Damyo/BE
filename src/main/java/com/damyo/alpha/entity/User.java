package com.damyo.alpha.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private String profileUrl;
    private int contribution;
    private String gender;
    private int age;

    @OneToMany(mappedBy = "user")
    private List<Picture> pictures = new ArrayList<>();
}
