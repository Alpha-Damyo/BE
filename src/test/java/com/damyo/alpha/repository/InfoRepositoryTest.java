package com.damyo.alpha.repository;

import com.damyo.alpha.domain.Info;
import com.damyo.alpha.domain.SmokingArea;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class InfoRepositoryTest {

    @Autowired
    private InfoRepository infoRepository;
    @Autowired
    private SmokingAreaRepository smokingAreaRepository;


    @BeforeEach
    void insertDummies() {
        SmokingArea smokingArea1 = smokingAreaRepository.save(
                SmokingArea.builder().id("1")
                        .name("국민대")
                        .createdAt(LocalDateTime.now())
                        .status(true)
                        .address("서울특별시 성북구").build()
        );
        SmokingArea smokingArea2 = smokingAreaRepository.save(
                SmokingArea.builder().id("2")
                        .name("한양8차")
                        .createdAt(LocalDateTime.now())
                        .status(true)
                        .address("인턴광역시 부평구").build()
        );

        Info info1 = infoRepository.save(
                Info.builder().smokingArea(smokingArea1).score(4).build()
        );
        Info info2 = infoRepository.save(
                Info.builder().smokingArea(smokingArea1).score(5).build()
        );
        Info info3 = infoRepository.save(
                Info.builder().smokingArea(smokingArea2).score(4).build()
        );
        Info info4 = infoRepository.save(
                Info.builder().smokingArea(smokingArea2).score(3).build()
        );
    }


    @Test
    void deleteInfoBySmokingAreaIdAndId() {
        List<Info> infos = infoRepository.findAll();
        for (Info info : infos) {
            System.out.println(info.getId());
        }
        infoRepository.deleteRecentInfoBySmokingAreaId("2");
        infos = infoRepository.findAll();
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