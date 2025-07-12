package com.openclassrooms.starterjwt.models;

import com.openclassrooms.starterjwt.repository.UserRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("test")
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        // Ensure the schema is created
        entityManager.getEntityManager().createNativeQuery(
                "CREATE TABLE IF NOT EXISTS users (" +
                        "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                        "email VARCHAR(50) UNIQUE, " +
                        "first_name VARCHAR(20), " +
                        "last_name VARCHAR(20), " +
                        "password VARCHAR(120), " +
                        "admin BOOLEAN, " +
                        "created_at TIMESTAMP, " +
                        "updated_at TIMESTAMP)")
                .executeUpdate();
    }

    @AfterEach
    void tearDown() {
        entityManager.clear();
    }

    private User createTestUser(String email) {
        return User.builder()
                .email(email)
                .firstName("Test")
                .lastName("User")
                .password("password123")
                .admin(false)
                .build();
    }

    @Test
    @Transactional
    void should_store_a_user() {
        User user = createTestUser("test@example.com");
        User savedUser = userRepository.saveAndFlush(user);

        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    @Transactional
    void should_find_user_by_id() {
        User user = createTestUser("test@example.com");
        User savedUser = userRepository.saveAndFlush(user);

        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get()).isEqualTo(savedUser);
    }

    @Test
    @Transactional
    void should_delete_user() {
        User user = createTestUser("delete@example.com");
        User savedUser = userRepository.saveAndFlush(user);

        userRepository.delete(savedUser);
        userRepository.flush();

        assertThat(userRepository.findById(savedUser.getId())).isEmpty();
    }

    @Test
    @Transactional
    void should_enforce_email_uniqueness() {
        userRepository.saveAndFlush(createTestUser("duplicate@example.com"));

        assertThrows(DataIntegrityViolationException.class, () -> {
            userRepository.saveAndFlush(createTestUser("duplicate@example.com"));
        });
    }

    @Test
    void should_auto_populate_timestamps() {
        User testUser = createTestUser("test@example.com");
        User user = userRepository.save(testUser);
        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getUpdatedAt());
    }
}