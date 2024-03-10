package com.damyo.alpha.repository;

import com.damyo.alpha.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    Challenge save(Challenge challenge);
    Optional<Challenge> findChallengeByName(String name);
    void deleteByName(String name);

}
