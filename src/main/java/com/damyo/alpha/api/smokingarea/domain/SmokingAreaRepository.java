package com.damyo.alpha.api.smokingarea.domain;

import com.damyo.alpha.api.smokingarea.domain.SmokingArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

    @Modifying(clearAutomatically = true)
    @Query("UPDATE SmokingArea sa SET sa.score = :score " +
            ", sa.opened = :opened " +
            ", sa.closed = :closed " +
            ", sa.hygiene = :hygiene " +
            ", sa.dirty = :dirty " +
            ", sa.airOut = :airOut " +
            ", sa.indoor = :indoor " +
            ", sa.outdoor = :outdoor " +
            ", sa.big = :big " +
            ", sa.small = :small " +
            ", sa.crowded = :crowded " +
            ", sa.quite = :quite " +
            ", sa.chair = :chair " +
            ", sa.noExist = :noExist " +
            "WHERE sa.id = :id")
    void updateSmokingAreaInfoById(@Param("opened") boolean opened, @Param("closed") boolean closed,
                                   @Param("hygiene") boolean hygiene, @Param("dirty") boolean dirty,
                                   @Param("airOut") boolean airOut, @Param("score") float score,
                                   @Param("indoor") boolean indoor, @Param("outdoor") boolean outdoor,
                                   @Param("big") boolean big, @Param("small") boolean small,
                                   @Param("crowded") boolean crowded, @Param("quite") boolean quite,
                                   @Param("chair") boolean chair, @Param("noExist") boolean noExist, @Param("id") String id);


    void deleteById(String id);

    @Query("SELECT sa FROM SmokingArea sa " +
            "WHERE sa.id LIKE %:region%")
    List<SmokingArea> findSmokingAreaByRegion(@Param("region") String region);
}
