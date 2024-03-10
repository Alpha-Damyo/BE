package com.damyo.alpha.repository;

import com.damyo.alpha.entity.SmokingArea;
import com.damyo.alpha.entity.SmokingData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SmokingDataRepository extends JpaRepository<SmokingData, BigDecimal> {
    SmokingData save(SmokingData smokingData);

    @Modifying
    @Query("SELECT sd FROM SmokingData sd " +
            "WHERE sd.smoking_area_id = :areaId")
    List<SmokingData> findSmokingDataBySmokingAreaId(@Param("areaId") BigDecimal smokingAreaId);

    void updateSmokingDataById();

    void deleteSmokingDataById();
}
