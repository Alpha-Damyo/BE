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

    @Query(value = "select rk " +
           "from (select id, round(round(percent_rank() over(order by contribution desc), 3) * 100, 2) as rk from users) as sub " +
           "where id = :id", nativeQuery = true)
    float getPercentageByContribution(UUID id);

//    @Query(value = "select rk " +
//                   "from (select id, dense_rank() over(order by contribution desc) as rk from users) as sub " +
//                   "where id = :id", nativeQuery = true)
//    int getMyRank(UUID id);

    @Query(value = "select contribution " +
                   "from users " +
                   "order by contribution desc " +
                   "limit 1", nativeQuery = true)
    int getTop1Contribution();

    Optional<User> findUserByProviderId(String providerId);
}
