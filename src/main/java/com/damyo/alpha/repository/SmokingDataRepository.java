package com.damyo.alpha.repository;

import com.damyo.alpha.domain.SmokingData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SmokingDataRepository extends JpaRepository<SmokingData, Long> {
    SmokingData save(SmokingData smokingData);

    @Modifying
    @Query("SELECT sd FROM SmokingData sd " +
            "WHERE sd.smokingArea = :areaId")
    List<SmokingData> findSmokingDataBySmokingAreaId(@Param("areaId") String smokingArea);

//    void updateSmokingDataById();
//
//    void deleteSmokingDataById();

}
