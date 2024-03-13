package com.damyo.alpha.repository;

import com.damyo.alpha.domain.SmokingArea;
import com.damyo.alpha.domain.SmokingData;
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
public class SmokingDataRepositoryTest {
    @Autowired
    private SmokingDataRepository smokingDataRepository;
    @Autowired
    private SmokingAreaRepository smokingAreaRepository;

    @BeforeEach()
    void createData() {
        smokingDataRepository.save(SmokingData.builder()
                .createdAt(LocalDateTime.now()).build());
    }

//    @Test
//    @DisplayName("findSmokingDataBySmokingAreaId")
//    void updateDataById(){
//        smokingAreaRepository.updateSmokingAreaNameById("국민대 미래관", 1L);
//        List<SmokingArea> area = smokingAreaRepository.findSmokingAreaById(1L);
//        Assertions.assertThat(area.get(0).getName()).isEqualTo("국민대 미래관");
//    }

}
