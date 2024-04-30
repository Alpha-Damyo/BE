package com.damyo.alpha.api.star.domain;

import com.damyo.alpha.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StarRepository extends JpaRepository<Star, Long> {
    List<Star> findStarsByUser(User user);
    Optional<Star> findStarById(Long id);
}
