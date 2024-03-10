package com.damyo.alpha.repository;

import com.damyo.alpha.entity.SmokingArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SmokingAreaRepository extends JpaRepository<SmokingArea, BigDecimal> {
    SmokingArea save(SmokingArea smokingArea);

    @Modifying
    @Query("SELECT sa FROM SmokingArea sa " +
            "WHERE sa.name = :name")
    List<SmokingArea> findSmokingAreaByName(@Param("name") String name);
    @Modifying
    @Query("SELECT sa FROM SmokingArea sa " +
            "WHERE sa.id = :id")
    List<SmokingArea> findSmokingAreaById(@Param("id") BigDecimal id);

    @Modifying
    @Query("UPDATE SmokingArea sa SET sa.name = :name " +
            "WHERE sa.id = :id")
    void updateSmokingAreaNameById(@Param("name") String name, @Param("id") BigDecimal id);
    @Modifying
    @Query("UPDATE SmokingArea sa SET sa.status = 'false' " +
            "WHERE sa.id = :id " +
            "AND sa.status = 'true'")
    void updateSmokingAreaStatusById(@Param("id") BigDecimal id);

    void deleteSmokingAreaById();
}
