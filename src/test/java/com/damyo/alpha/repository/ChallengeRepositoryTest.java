package com.damyo.alpha.repository;

import com.damyo.alpha.api.challenge.domain.Challenge;
import com.damyo.alpha.api.challenge.domain.ChallengeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        Challenge challenge3 = challengeRepository.save(
                Challenge.builder().
                        name("c3").
                        startTime(LocalDateTime.now().minusDays(2L)).
                        endTime(LocalDateTime.now().minusDays(1L)).
                        bannerImgUrl("c").
                        detailImgUrl("d").
                        build()
        );
        Challenge challenge4 = challengeRepository.save(
                Challenge.builder().
                        name("c4").
                        startTime(LocalDateTime.now().plusDays(1L)).
                        endTime(LocalDateTime.now().plusDays(2L)).
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
    @DisplayName("현 시간 활성화 챌린지 조회")
    void findAllByCurrentTime() {
        List<Challenge> challenges = challengeRepository.findAllByCurrentTime(LocalDateTime.now());
        for (Challenge challenge : challenges) {
            System.out.println(challenge.getName());
        }
    }

    @Test
    @DisplayName("챌린지 조회")
    void findAll() {
        List<Challenge> challenges = challengeRepository.findAll();
        for (Challenge challenge : challenges) {
            System.out.println(challenge.getName());
        }
    }

//    @Test
//    void deleteByName() {
//
//    }
}