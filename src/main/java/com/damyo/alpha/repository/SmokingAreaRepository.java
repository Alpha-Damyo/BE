package com.damyo.alpha.repository;

import com.damyo.alpha.domain.SmokingArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SmokingAreaRepository extends JpaRepository<SmokingArea, String> {
    SmokingArea save(SmokingArea smokingArea);

    List<SmokingArea> findAll();

    @Query("SELECT sa FROM SmokingArea sa " +
            "WHERE sa.name = :name")
    List<SmokingArea> findSmokingAreaByName(@Param("name") String name);

    @Query("SELECT sa FROM SmokingArea sa " +
            "WHERE sa.id = :id")
    SmokingArea findSmokingAreaById(@Param("id") String id);

    @Query("SELECT sa FROM SmokingArea sa " +
            "WHERE sa.createdAt >= :localDate")
    List<SmokingArea> findSmokingAreaByCreatedAt(@Param("localDate")LocalDateTime date);

    // TODO 추후 개선 고민
    @Query("SELECT sa FROM SmokingArea sa " +
            "WHERE sa.id LIKE %:areaName% " +
            "ORDER BY sa.createdAt DESC " +
            "LIMIT 1")
    SmokingArea findSmokingAreaIdByAreaName(@Param("areaName") String areaName);

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

    @Modifying(clearAutomatically = true)
    @Query("UPDATE SmokingArea sa SET sa.score = :score " +
            "WHERE sa.id = :id")
    void updateSmokingAreaScoreById(@Param("score") Float score, @Param("id") String id);


    void deleteById(String id);

    @Query("SELECT  sa FROM SmokingArea sa " +
            "WHERE sa.latitude BETWEEN :minLa AND :maxLa " +
            "AND sa.longitude BETWEEN :minLo AND :maxLo")
    List<SmokingArea> findSmokingAreaByCoordinate(@Param("minLa") BigDecimal minLatitude, @Param("maxLa") BigDecimal maxLatitude, @Param("minLo") BigDecimal minLongitude, @Param("maxLo") BigDecimal maxLongitude, @Param("range") BigDecimal range);

    @Query("SELECT sa FROM SmokingArea sa " +
            "WHERE sa.id LIKE %:region%")
    List<SmokingArea> findSmokingAreaByRegion(@Param("region") String region);

    // TODO 추후 개선 고민
    @Query("SELECT sa FROM SmokingArea sa " +
            "WHERE (sa.id LIKE %:word% OR sa.address LIKE %:word%) " +
            "AND sa.status = :temp " +
            "AND sa.opened = :opened " +
            "AND sa.closed = :closed " +
            "AND sa.hygiene = :hygiene " +
            "AND sa.airOut = :airOut " +
            "AND sa.indoor = :indoor " +
            "AND sa.outdoor = :outdoor " +
            "AND sa.big = :big " +
            "AND sa.small = :small " +
            "AND sa.crowded = :crowded " +
            "AND sa.quite = :quite " +
            "AND sa.chair = :chair")
    List<SmokingArea> findSmokingAreaByQuery(@Param("word") String word, @Param("temp") boolean temp,
                                             @Param("opened") boolean opened, @Param("closed") boolean closed,
                                             @Param("hygeine") boolean hygeine, @Param("airOut") boolean airOut,
                                             @Param("indoor") boolean indoor, @Param("outdoor") boolean outdoor,
                                             @Param("big") boolean big, @Param("small") boolean small,
                                             @Param("crowded") boolean crowded, @Param("quite") boolean quite,
                                             @Param("chair") boolean chair);
}
