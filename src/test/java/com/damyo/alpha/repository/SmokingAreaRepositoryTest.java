package com.damyo.alpha.repository;

import com.damyo.alpha.api.smokingarea.domain.SmokingArea;

import com.damyo.alpha.api.smokingarea.domain.SmokingAreaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
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
                                    .latitude(BigDecimal.valueOf(35.43345))
                                    .longitude(BigDecimal.valueOf(125.2321))
                                    .createdAt(LocalDateTime.now())
                                    .status(true)
                                    .address("서울특별시 성북구")
                                    .description("대학교").build());
    }


    @Test
    @DisplayName("findSmokingAreaByCreatedAt")
    void findAreaByCreatedAt(){
        List<SmokingArea> areas = smokingAreaRepository.findSmokingAreaByCreatedAt(LocalDateTime.of(2021,1,1,0,0,0));
        Assertions.assertThat(areas.get(0).getName()).isEqualTo("국민대");
    }

    @Test
    @DisplayName("updateSmokingAreaNameById")
    void updateAreaNameById(){
        smokingAreaRepository.updateSmokingAreaNameById("국민대 미래관", "1");
        SmokingArea area = smokingAreaRepository.findSmokingAreaById("1");
        Assertions.assertThat(area.getName()).isEqualTo("국민대 미래관");
    }

    @Test
    @DisplayName("updateSmokingAreaStatusById")
    void updateAreaStatusById(){
        smokingAreaRepository.updateSmokingAreaStatusById("1");
        SmokingArea area = smokingAreaRepository.findSmokingAreaById("1");
        Assertions.assertThat(area.isStatus()).isEqualTo(false);
    }

    @Test
    @DisplayName("updateSmokingAreaAddressById")
    void updateSmokingAreaAddressById(){
        smokingAreaRepository.updateSmokingAreaAddressById("서울특별시 성북구 정릉로 77","1");
        SmokingArea area = smokingAreaRepository.findSmokingAreaById("1");
        Assertions.assertThat(area.getAddress()).isEqualTo("서울특별시 성북구 정릉로 77");
    }

    @Test
    @DisplayName("updateSmokingAreaDescriptionById")
    void updateSmokingAreaDescriptionById(){
        smokingAreaRepository.updateSmokingAreaDescriptionById("미래관 3층","1");
        SmokingArea area = smokingAreaRepository.findSmokingAreaById("1");
        Assertions.assertThat(area.getDescription()).isEqualTo("미래관 3층");
    }


}
