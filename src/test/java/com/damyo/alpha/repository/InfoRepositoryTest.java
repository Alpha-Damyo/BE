package com.damyo.alpha.repository;

import com.damyo.alpha.entity.Info;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class InfoRepositoryTest {

    @Autowired
    private InfoRepository infoRepository;

    @BeforeEach
    void insertDummies() {
        Info info1 = infoRepository.save(
                Info.builder().id(1L).smokingAreaId(1L).score(4).build()
        );
        Info info2 = infoRepository.save(
                Info.builder().id(2L).smokingAreaId(1L).score(5).build()
        );
        Info info3 = infoRepository.save(
                Info.builder().id(1L).smokingAreaId(2L).score(4).build()
        );
        Info info4 = infoRepository.save(
                Info.builder().id(2L).smokingAreaId(2L).score(3).build()
        );
    }

    @Test
    void deleteInfoBySmokingAreaIdAndId() {
    }

    @Test
    void findInfoBySmokingAreaId() {
    }
}