package com.damyo.alpha.api.smokingdata.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface SmokingDataRepository extends JpaRepository<SmokingData, Long> {
    SmokingData save(SmokingData smokingData);

    @Query("SELECT sd FROM SmokingData sd " +
            "WHERE sd.id = :id")
    SmokingData findSmokingDataById(@Param("id") Long id);
    @Query("SELECT sd FROM SmokingData sd " +
            "WHERE sd.smokingArea.id = :areaId")
    List<SmokingData> findSmokingDataBySmokingAreaId(@Param("areaId") String smokingAreaId);

    @Query("SELECT sd FROM SmokingData sd " +
            "WHERE sd.user.id = :userId")
    List<SmokingData> findSmokingDataByUserId(@Param("userId") UUID userId);

    @Query("SELECT sd FROM SmokingData sd " +
            "WHERE sd.createdAt >= :startTime " +
            "AND sd.createdAt <= :endTime")
    List<SmokingData> findSmokingDataByCreateAt(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE SmokingData sd SET sd.createdAt = :localDate " +
            "WHERE sd.id = :id")
    void updateSmokingDataCreateAtById(@Param("localDate") LocalDateTime date, @Param("id") Long id);


    void deleteById(Long id);
}
