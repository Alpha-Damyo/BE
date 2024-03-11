package com.damyo.alpha.repository;

import com.damyo.alpha.domain.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    Challenge save(Challenge challenge);
    Optional<Challenge> findChallengeByName(String name);
    void deleteByName(String name);

    List<Challenge> findAll();

    @Modifying
    @Query("SELECT ch FROM Challenge ch " +
            "WHERE ch.startTime <= :datetime AND ch.endTime >= :datetime")
    List<Challenge> findAllByCurrentTime(@Param("datetime") LocalDateTime dateTime);

}
