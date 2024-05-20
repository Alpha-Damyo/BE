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

    @Query("SELECT  sa FROM SmokingArea sa " +
            "WHERE sa.latitude BETWEEN :minLa AND :maxLa " +
            "AND sa.longitude BETWEEN :minLo AND :maxLo " +
            "AND (:status = TRUE AND sa.status = TRUE) " +
            "AND (:open = TRUE AND sa.opened = TRUE) " +
            "AND (:close = TRUE AND sa.closed = TRUE) " +
            "AND (:indoor = TRUE AND sa.indoor = TRUE) " +
            "AND (:outdoor = TRUE AND sa.outdoor = TRUE) " +
            "AND (:hygeine = TRUE AND sa.hygiene = TRUE) " +
            "AND (:chair = TRUE AND sa.chair = TRUE)")
    List<SmokingArea> findSmokingAreaByCoordinate(@Param("minLa") BigDecimal minLatitude, @Param("maxLa") BigDecimal maxLatitude,
                                                  @Param("minLo") BigDecimal minLongitude, @Param("maxLo") BigDecimal maxLongitude,
                                                  @Param("status") Boolean status, @Param("open") Boolean open, @Param("close") Boolean close,
                                                  @Param("indoor") Boolean indoor, @Param("outdoor") Boolean outdoor, @Param("hygiene") Boolean hygiene, @Param("chair") Boolean chair);

    @Query("SELECT sa FROM SmokingArea sa " +
            "WHERE sa.id LIKE %:region%")
    List<SmokingArea> findSmokingAreaByRegion(@Param("region") String region);

    // TODO 추후 개선 고민
    @Query("SELECT sa FROM SmokingArea sa " +
            "WHERE (sa.id LIKE %:word% OR sa.address LIKE %:word%) " +
            "AND (:temp = TRUE AND sa.status = TRUE) " +
            "AND (:opened = TRUE AND sa.opened = TRUE) " +
            "AND (:closed = TRUE AND sa.closed = TRUE) " +
            "AND (:hygiene = TRUE AND sa.hygiene = TRUE) " +
            "AND (:airOut = TRUE AND sa.airOut = TRUE) " +
            "AND (:indoor = TRUE AND sa.indoor = TRUE) " +
            "AND (:outdoor = TRUE AND sa.outdoor = TRUE) " +
            "AND (:big = TRUE AND sa.big = TRUE) " +
            "AND (:small = TRUE AND sa.small = TRUE) " +
            "AND (:crowded = TRUE AND sa.crowded = TRUE) " +
            "AND (:quite = TRUE AND sa.quite = TRUE) " +
            "AND (:chair = TRUE AND sa.chair = TRUE)")
    List<SmokingArea> findSmokingAreaByQuery(@Param("word") String word, @Param("temp") boolean temp,
                                             @Param("opened") boolean opened, @Param("closed") boolean closed,
                                             @Param("hygiene") boolean hygiene, @Param("airOut") boolean airOut,
                                             @Param("indoor") boolean indoor, @Param("outdoor") boolean outdoor,
                                             @Param("big") boolean big, @Param("small") boolean small,
                                             @Param("crowded") boolean crowded, @Param("quite") boolean quite,
                                             @Param("chair") boolean chair);
}
