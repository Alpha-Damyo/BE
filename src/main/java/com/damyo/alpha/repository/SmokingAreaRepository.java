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


    @Query("SELECT sa FROM SmokingArea sa " +
            "WHERE sa.name = :name")
    List<SmokingArea> findSmokingAreaByName(@Param("name") String name);

    @Query("SELECT sa FROM SmokingArea sa " +
            "WHERE sa.id = :id")
    SmokingArea findSmokingAreaById(@Param("id") String id);

    @Query("SELECT sa FROM SmokingArea sa " +
            "WHERE sa.createdAt >= :localDate")
    List<SmokingArea> findSmokingAreaByCreatedAt(@Param("localDate")LocalDateTime date);


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


    void deleteById(String id);

}
