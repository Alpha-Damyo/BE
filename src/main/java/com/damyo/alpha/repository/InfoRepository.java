package com.damyo.alpha.repository;

import com.damyo.alpha.domain.Info;
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
    List<Info> findInfosBySmokingAreaId(String smokingAreaId);

}
