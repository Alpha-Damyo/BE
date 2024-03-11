package com.damyo.alpha.repository;

import com.damyo.alpha.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void insertUser() {
        userRepository.save(User.builder()
                        .name("홍길동")
                        .email("example@gmail.com")
                        .profileUrl("before")
                        .createdAt(LocalDateTime.now())
                        .contribution(100).build()
        );
    }

    @Test
    @DisplayName("이름_변경O")
    void updateNameByEmail() {
        userRepository.updateNameByEmail("홍길서", "example@gmail.com");
        Optional<User> user = userRepository.findUserByEmail("example@gmail.com");
        user.ifPresent(u -> assertThat(u.getName()).isEqualTo("홍길서"));
    }

    @Test
    @DisplayName("프로필_변경O")
    void updateProfileUrlByEmail() {
        userRepository.updateProfileUrlByEmail("after", "example@gmail.com");
        Optional<User> user = userRepository.findUserByEmail("example@gmail.com");
        user.ifPresent(u -> assertThat(u.getProfileUrl()).isEqualTo("after"));
    }

    @Test
    @DisplayName("기여도_증가O")
    void updateContributionByEmail() {
        userRepository.updateContributionByEmail(50, "example@gmail.com");
        Optional<User> user = userRepository.findUserByEmail("example@gmail.com");
        user.ifPresent(u -> assertThat(u.getContribution()).isEqualTo(150));
    }
}