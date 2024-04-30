package com.damyo.alpha.api.info.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InfoRepository extends JpaRepository<Info, Long> {
    Info save(Info info);
    @Modifying
    @Query("DELETE FROM Info WHERE id = (SELECT id FROM (SELECT min(id) AS id FROM Info WHERE smokingArea.id = :saId) AS tmp)")
    // delete from Info where id in () order by id
    void deleteRecentInfoBySmokingAreaId(@Param("saId") String smokingAreaId);
    @Query("SELECT i FROM Info i WHERE i.smokingArea.id = :saId")
    List<Info> findInfosBySmokingAreaId(@Param("saId") String smokingAreaId);

}
