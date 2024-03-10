package com.damyo.alpha.repository;

import com.damyo.alpha.entity.Challenge;
import com.damyo.alpha.entity.Info;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ChallengeRepositoryTest {

    @Autowired
    private ChallengeRepository challengeRepository;

    @BeforeEach
    void insertDummies() {
        Challenge challenge1 = challengeRepository.save(
                Challenge.builder().
                        name("c1").
                        startTime(LocalDateTime.now()).
                        endTime(LocalDateTime.now().plusDays(2L)).
                        bannerImgUrl("a").
                        detailImgUrl("b").
                        build()
        );
        Challenge challenge2 = challengeRepository.save(
                Challenge.builder().
                        name("c2").
                        startTime(LocalDateTime.now().minusDays(1L)).
                        endTime(LocalDateTime.now().plusDays(1L)).
                        bannerImgUrl("c").
                        detailImgUrl("d").
                        build()
        );
    }

    @Test
    @DisplayName("챌린지 이름 조회")
    void findChallengeByName() {
        Optional<Challenge> challenge = challengeRepository.findChallengeByName("c1");
        assert challenge.isPresent();
    }

    @Test
    void deleteByName() {

    }
}