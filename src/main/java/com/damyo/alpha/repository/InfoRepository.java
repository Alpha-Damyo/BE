package com.damyo.alpha.repository;

import com.damyo.alpha.domain.Info;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InfoRepository extends JpaRepository<Info, Long> {
    Info save(Info info);
    void deleteInfoBySmokingAreaIdAndId(String smokingAreaId ,Long id);
    List<Info> findInfosBySmokingAreaId(String smokingAreaId);

}
