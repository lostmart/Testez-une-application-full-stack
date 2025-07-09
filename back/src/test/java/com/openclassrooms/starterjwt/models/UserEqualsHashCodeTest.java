package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class UserEqualsHashCodeTest {

    private User createSampleUser() {
        return User.builder()
                .id(1L)
                .email("user@example.com")
                .lastName("Doe")
                .firstName("John")
                .password("secret")
                .admin(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void testEquals_sameInstance() {
        User user = createSampleUser();
        assertEquals(user, user);
    }

    @Test
    void testEquals_null() {
        User user = createSampleUser();
        assertNotEquals(user, null);
    }

    @Test
    void testEquals_differentClass() {
        User user = createSampleUser();
        assertNotEquals(user, "not a user");
    }

    @Test
    void testEquals_sameId() {
        User user1 = createSampleUser();
        User user2 = createSampleUser(); // same ID
        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void testEquals_differentId() {
        User user1 = createSampleUser();
        User user2 = createSampleUser().setId(2L);
        assertNotEquals(user1, user2);
    }

    @Test
    void testHashCode_consistency() {
        User user = createSampleUser();
        int hash1 = user.hashCode();
        int hash2 = user.hashCode();
        assertEquals(hash1, hash2);
    }

    @Test
    void testGettersAndSetters() {
        User user = new User();
        user.setId(42L);
        user.setEmail("test@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("pass123");
        user.setAdmin(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        assertEquals("test@example.com", user.getEmail());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("pass123", user.getPassword());
        assertTrue(user.isAdmin());
        assertEquals(42L, user.getId());
    }

    @Test
    void testToStringAndHashCode() {
        User user = User.builder()
                .id(1L)
                .email("test@example.com")
                .firstName("Alice")
                .lastName("Doe")
                .password("pass")
                .admin(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        assertNotNull(user.toString());
        assertDoesNotThrow(user::hashCode);
    }
}
