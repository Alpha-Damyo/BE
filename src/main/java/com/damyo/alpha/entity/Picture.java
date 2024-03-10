package com.damyo.alpha.entity;

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
public class Picture {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pictureUrl;
    private LocalDateTime createdAt;
    private int likes;

    @ManyToOne
    private User user;
}
