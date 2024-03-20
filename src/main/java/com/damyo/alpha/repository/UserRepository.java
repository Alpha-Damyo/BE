package com.damyo.alpha.repository;

import com.damyo.alpha.domain.User;
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
    @Query("update User u set u.name = :name where u.email = :email")
    void updateNameByEmail(String name, String email);
    @Modifying(clearAutomatically = true)
    @Query("update User u set u.profileUrl = :profileUrl where u.email = :email")
    void updateProfileUrlByEmail(String profileUrl, String email);
    @Modifying(clearAutomatically = true)
    @Query("update User u set u.contribution = u.contribution + :increment where u.email = :email")
    void updateContributionByEmail(int increment, String email);
    void deleteUserById(UUID id);
}
