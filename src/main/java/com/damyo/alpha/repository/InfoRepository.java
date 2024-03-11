package com.damyo.alpha.repository;

import com.damyo.alpha.entity.Info;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InfoRepository extends JpaRepository<Info, Long> {
    Info save(Info info);
    void deleteInfoBySmokingAreaIdAndId(Long smokingAreaId ,Long id);
    List<Info> findInfosBySmokingAreaId(Long smokingAreaId);

}
