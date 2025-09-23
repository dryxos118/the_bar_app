package com.example.the_bar_app.repository;

import com.example.the_bar_app.entity.RoleName;
import com.example.the_bar_app.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUsername_andEmail_shouldWork() {
        // Arrange
        User user = User.builder()
                .username("dryx")
                .email("dryx@thebar.app")
                .password("hash")
                .role(RoleName.USER)
                .enabled(true)
                .build();
        userRepository.save(user);
        // Act & Assert
        assertThat(userRepository.findByUsername("dryx")).isPresent();
        assertThat(userRepository.findByEmail("dryx@thebar.app")).isPresent();
        assertThat(userRepository.existsByUsername("dryx")).isTrue();
        assertThat(userRepository.existsByEmail("dryx@thebar.app")).isTrue();
    }
}
