package com.damyo.alpha.repository;

import com.damyo.alpha.domain.Info;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class InfoRepositoryTest {

    @Autowired
    private InfoRepository infoRepository;

    @BeforeEach
    void insertDummies() {
        Info info1 = infoRepository.save(
                Info.builder().smokingAreaId(1L).score(4).build()
        );
        Info info2 = infoRepository.save(
                Info.builder().smokingAreaId(1L).score(5).build()
        );
        Info info3 = infoRepository.save(
                Info.builder().smokingAreaId(2L).score(4).build()
        );
        Info info4 = infoRepository.save(
                Info.builder().smokingAreaId(2L).score(3).build()
        );
    }

    @Test
    void deleteInfoBySmokingAreaIdAndId() {
        List<Info> infos = infoRepository.findAll();
        for (Info info : infos) {
            System.out.println(info.getId());
        }
        infoRepository.deleteInfoBySmokingAreaIdAndId("2", 2L);
        for (Info info : infos) {
            System.out.println(info.getId());
        }
    }

    @Test
    void findInfosBySmokingAreaId() {
        List<Info> infos = infoRepository.findInfosBySmokingAreaId("2");
        for (Info info : infos) {
            System.out.println(info.getId());
        }
    }
}