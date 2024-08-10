package com.damyo.alpha.api.smokingarea.domain;

import com.damyo.alpha.api.smokingarea.domain.SmokingArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SmokingAreaRepository extends JpaRepository<SmokingArea, String>, SmokingAreaCustomRepository {
    SmokingArea save(SmokingArea smokingArea);

    List<SmokingArea> findAll();

    @Query("SELECT sa FROM SmokingArea sa " +
            "WHERE sa.name = :name")
    List<SmokingArea> findSmokingAreaByName(@Param("name") String name);

    @Query("SELECT sa FROM SmokingArea sa " +
            "WHERE sa.id = :id")
    Optional<SmokingArea> findSmokingAreaById(@Param("id") String id);

    @Query("SELECT sa FROM SmokingArea sa " +
            "WHERE sa.createdAt >= :localDate")
    List<SmokingArea> findSmokingAreaByCreatedAt(@Param("localDate")LocalDateTime date);

    @Query("SELECT sa FROM SmokingArea sa " +
            "WHERE sa.id LIKE %:areaName% " +
            "ORDER BY sa.createdAt DESC " +
            "LIMIT 1")
    Optional<SmokingArea> findSmokingAreaIdByAreaName(@Param("areaName") String areaName);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE SmokingArea sa SET sa.name = :name " +
            "WHERE sa.id = :id")
    void updateSmokingAreaNameById(@Param("name") String name, @Param("id") String id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE SmokingArea sa SET sa.status = false " +
            "WHERE sa.id = :id " +
            "AND sa.status = true")
    void updateSmokingAreaStatusById(@Param("id") String id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE SmokingArea sa SET sa.address = :address " +
            "WHERE sa.id = :id")
    void updateSmokingAreaAddressById(@Param("address") String address, @Param("id") String id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE SmokingArea sa SET sa.description = :description " +
            "WHERE sa.id = :id")
    void updateSmokingAreaDescriptionById(@Param("description") String description, @Param("id") String id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE SmokingArea sa SET sa.isActive = false " +
            "WHERE sa.id = :id")
    void updateSmokingAreaActiveById(@Param("id") String id);

//   업데이트 기능 일시 삭제
//    @Modifying(clearAutomatically = true)
//    @Query("UPDATE SmokingArea sa SET sa.score = :score " +
//            ", sa.opened = :opened " +
//            ", sa.closed = :closed " +
//            ", sa.indoor = :indoor " +
//            ", sa.outdoor = :outdoor " +
//            "WHERE sa.id = :id")
//    void updateSmokingAreaInfoById(@Param("opened") boolean opened, @Param("closed") boolean closed,
//                                   @Param("score") float score,
//                                   @Param("indoor") boolean indoor, @Param("outdoor") boolean outdoor, @Param("id") String id);


    void deleteById(String id);

    @Query("SELECT sa FROM SmokingArea sa " +
            "WHERE sa.id LIKE %:region%")
    List<SmokingArea> findSmokingAreaByRegion(@Param("region") String region);
}
