package com.damyo.alpha.api.user.domain;

import com.damyo.alpha.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User save(User user);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserById(UUID id);
    @Modifying(clearAutomatically = true)
    @Query("update User u set u.name = :name where u.id = :id")
    void updateNameById(String name, UUID id);
    @Modifying(clearAutomatically = true)
    @Query("update User u set u.profileUrl = :profileUrl where u.id = :id")
    void updateProfileUrlById(String profileUrl, UUID id);
    @Modifying(clearAutomatically = true)
    @Query("update User u set u.contribution = u.contribution + :increment where u.id = :id")
    void updateContributionById(int increment, UUID id);
    void deleteUserById(UUID id);
}
