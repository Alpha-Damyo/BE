package com.damyo.alpha.repository;

import com.damyo.alpha.domain.Picture;
import com.damyo.alpha.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PictureRepositoryTest {

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void InsertDummies() {
        User user1 = userRepository.save(
                User.builder()
                        .email("example1@gmail.com")
                        .name("홍길동1")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        User user2 = userRepository.save(
                User.builder()
                        .email("example2@gmail.com")
                        .name("홍길동2")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        IntStream.rangeClosed(1, 10).forEach(i -> {
            Picture picture = Picture.builder()
                    .pictureUrl("url" + i)
                    .createdAt(LocalDateTime.now())
                    .likes(0)
                    .user(user1)
                    .build();
            pictureRepository.save(picture);
        });

        IntStream.rangeClosed(11, 15).forEach(i -> {
            Picture picture = Picture.builder()
                    .pictureUrl("url" + i)
                    .createdAt(LocalDateTime.now())
                    .likes(0)
                    .user(user2)
                    .build();
            pictureRepository.save(picture);
        });
    }

    @Test
    @DisplayName("사진_목록_조회O")
    void findPicturesByUser_id() {
        Optional<User> user = userRepository.findUserByEmail("example2@gmail.com");
        assert user.isPresent();
        List<Picture> pictures = pictureRepository.findPicturesByUser_id(user.get().getId());
        int idx = 11;
        for (Picture picture : pictures) {
            Assertions.assertThat(picture.getPictureUrl()).isEqualTo("url" + idx);
            idx ++;
        }
    }
}
