package com.damyo.alpha.repository;

import com.damyo.alpha.domain.SmokingArea;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SmokingAreaRepositoryTest {

    @Autowired
    private SmokingAreaRepository smokingAreaRepository;

    @BeforeEach()
    void createArea() {
        smokingAreaRepository.save(SmokingArea.builder()
                                    .id("1")
                                    .name("국민대")
                                    .createdAt(LocalDateTime.now())
                                    .status(true)
                                    .address("서울특별시 성북구").build());
    }

    @Test
    @DisplayName("updateSmokingAreaNameById")
    void updateAreaNameById(){
        smokingAreaRepository.updateSmokingAreaNameById("국민대 미래관", "1");
        List<SmokingArea> area = smokingAreaRepository.findSmokingAreaById("1");
        Assertions.assertThat(area.get(0).getName()).isEqualTo("국민대 미래관");
    }

    @Test
    @DisplayName("updateSmokingAreaStatusById")
    void updateAreaStatusById(){
        smokingAreaRepository.updateSmokingAreaStatusById("1");
        List<SmokingArea> area = smokingAreaRepository.findSmokingAreaById("1");
        Assertions.assertThat(area.get(0).isStatus()).isEqualTo(false);
    }
}
