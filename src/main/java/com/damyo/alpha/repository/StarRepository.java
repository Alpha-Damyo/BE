package com.damyo.alpha.repository;

import com.damyo.alpha.domain.Star;
import com.damyo.alpha.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StarRepository extends JpaRepository<Star, Long> {
    List<Star> findStarsByUser(User user);
    Optional<Star> findStarById(Long id);
}
