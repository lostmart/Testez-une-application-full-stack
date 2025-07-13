package com.openclassrooms.starterjwt.models;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        // Clear any existing data
        entityManager.getEntityManager().createQuery("DELETE FROM User").executeUpdate();
    }

    @AfterEach
    void tearDown() {
        entityManager.clear();
    }

    @Test
    void should_store_a_user() {
        User user = User.builder()
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .password("password123")
                .admin(false)
                .build();

        User savedUser = userRepository.save(user);

        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo("test@example.com");
        assertThat(savedUser.getCreatedAt()).isNotNull();
    }

    @Test
    void should_find_user_by_id() {
        User user = entityManager.persist(createTestUser("find@me.com"));
        User foundUser = userRepository.findById(user.getId()).orElse(null);

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("find@me.com");
    }

    @Test
    void should_return_empty_for_nonexistent_id() {
        assertThat(userRepository.findById(999L)).isEmpty();
    }

    @Test
    void should_find_all_users() {
        entityManager.persist(createTestUser("user1@test.com"));
        entityManager.persist(createTestUser("user2@test.com"));

        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(2);
    }

    @Test
    void should_auto_populate_timestamps() {
        User user = userRepository.save(createTestUser("timestamp@test.com"));
        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getUpdatedAt());
    }

    @Test
    void should_enforce_email_uniqueness() {
        userRepository.save(createTestUser("unique@test.com"));

        User duplicateUser = createTestUser("unique@test.com");
        assertThrows(Exception.class, () -> {
            userRepository.save(duplicateUser);
            entityManager.flush();
        });
    }

    @Test
    void should_delete_user() {
        User user = entityManager.persist(createTestUser("delete@me.com"));

        userRepository.deleteById(user.getId());

        assertThat(userRepository.findById(user.getId())).isEmpty();
    }

    @Test
    void should_verify_admin_flag() {
        User admin = createTestUser("admin@test.com");
        admin.setAdmin(true);
        userRepository.save(admin);

        User regular = createTestUser("regular@test.com");
        userRepository.save(regular);

        User foundAdmin = userRepository.findById(admin.getId()).orElseThrow();
        User foundRegular = userRepository.findById(regular.getId()).orElseThrow();

        assertTrue(foundAdmin.isAdmin());
        assertFalse(foundRegular.isAdmin());
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
}