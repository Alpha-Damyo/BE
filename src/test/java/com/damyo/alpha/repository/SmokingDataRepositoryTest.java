package com.damyo.alpha.repository;

import com.damyo.alpha.domain.SmokingArea;
import com.damyo.alpha.domain.SmokingData;
import com.damyo.alpha.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SmokingDataRepositoryTest {
    @Autowired
    private SmokingDataRepository smokingDataRepository;
    @Autowired
    private SmokingAreaRepository smokingAreaRepository;

    @Autowired
    private UserRepository userRepository;

    private Long idNum;

    @BeforeEach()
    void createData() {
        User user =  userRepository.save(User.builder()
                        .name("홍길동1")
                        .email("example1@gmail.com")
                        .createdAt(LocalDateTime.now())
                        .build());

        SmokingArea area = smokingAreaRepository.save(SmokingArea.builder()
                        .id("1")
                        .name("국민대")
                        .createdAt(LocalDateTime.now())
                        .status(true)
                        .address("서울특별시 성북구")
                        .build());

        SmokingData data = smokingDataRepository.save(SmokingData.builder()
                        .id(1L)
                        .smokingArea(area)
                        .user(user)
                        .createdAt(LocalDateTime.now()).build());
        idNum = data.getId();
    }

    @Test
    @DisplayName("findSmokingDataById")
    void findSmokingDataById(){
        SmokingData data = smokingDataRepository.findSmokingDataById(idNum);
        Assertions.assertThat(data.getSmokingArea().getName()).isEqualTo("국민대");
    }

    @Test
    @DisplayName("findSmokingDataBySmokingAreaId")
    void findSmokingDataBySmokingAreaId(){
        List<SmokingData> dataList = smokingDataRepository.findSmokingDataBySmokingAreaId("1");
        Assertions.assertThat(dataList.get(0).getSmokingArea().getName()).isEqualTo("국민대");
    }

    @Test
    @DisplayName("findSmokingDataByUserId")
    void findSmokingDataByUserId(){
        Optional<User> target = userRepository.findUserByEmail("example1@gmail.com");
        List<SmokingData> dataList = smokingDataRepository.findSmokingDataByUserId(target.get().getId());
        Assertions.assertThat(dataList.get(0).getUser().getName()).isEqualTo("홍길동1");
    }

    @Test
    @DisplayName("findSmokingDataByCreateAt")
    void findSmokingDataByCreateAt(){
        List<SmokingData> dataList = smokingDataRepository.findSmokingDataByCreateAt(LocalDateTime.of(2021,1,1,1,1,1), LocalDateTime.of(2025,1,1,1,1,1));
        Assertions.assertThat(dataList.get(0).getSmokingArea().getName()).isEqualTo("국민대");
    }

    @Test
    @DisplayName("updateSmokingDataCreateAtById")
    void updateSmokingDataCreateAtById(){
        smokingDataRepository.updateSmokingDataCreateAtById(LocalDateTime.of(2024,7,7,7,7,7), idNum);
        SmokingData data = smokingDataRepository.findSmokingDataById(idNum);
        Assertions.assertThat(data.getCreatedAt()).isEqualTo(LocalDateTime.of(2024,7,7,7,7,7));
    }

    @Test
    @DisplayName("deleteSmokingDataById")
    void deleteSmokingDataById(){
        List<SmokingData> dataBefore = smokingDataRepository.findSmokingDataBySmokingAreaId("1");
        for (SmokingData data : dataBefore){
            System.out.println(data);
        }
        smokingDataRepository.deleteById(1L);
        List<SmokingData> dataAfter = smokingDataRepository.findSmokingDataBySmokingAreaId("1");
        for (SmokingData data : dataAfter){
            System.out.println(data);
        }
    }

}
