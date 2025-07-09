package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class UserModelCoverageTest {

    private User createUserWithId(Long id) {
        return User.builder()
                .id(id)
                .email("user@example.com")
                .firstName("John")
                .lastName("Doe")
                .password("secret")
                .admin(false)
                .createdAt(LocalDateTime.of(2024, 1, 1, 10, 0))
                .updatedAt(LocalDateTime.of(2024, 1, 2, 10, 0))
                .build();
    }

    @Test
    void testEquals_sameId_shouldBeEqual() {
        User user1 = createUserWithId(1L);
        User user2 = createUserWithId(1L);
        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void testEquals_differentId_shouldNotBeEqual() {
        User user1 = createUserWithId(1L);
        User user2 = createUserWithId(2L);
        assertNotEquals(user1, user2);
    }

    @Test
    void testEquals_nullId_shouldNotEqual() {
        User user1 = createUserWithId(null);
        User user2 = createUserWithId(1L);
        assertNotEquals(user1, user2);
    }

    @Test
    void testEquals_bothNullIds_shouldBeEqual() {
        User user1 = createUserWithId(null);
        User user2 = createUserWithId(null);
        assertEquals(user1, user2);
    }

    @Test
    void testEquals_differentClass_shouldNotBeEqual() {
        User user = createUserWithId(1L);
        assertNotEquals(user, "not a user");
    }

    @Test
    void testEquals_null_shouldNotBeEqual() {
        User user = createUserWithId(1L);
        assertNotEquals(user, null);
    }

    @Test
    void testToString_doesNotThrow() {
        User user = createUserWithId(1L);
        assertNotNull(user.toString());
    }
}
