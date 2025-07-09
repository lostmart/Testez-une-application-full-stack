package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TeacherTest {

    @Test
    void testSettersAndGetters() {
        LocalDateTime now = LocalDateTime.now();

        Teacher teacher = new Teacher();
        teacher.setId(42L);
        teacher.setFirstName("Alice");
        teacher.setLastName("Smith");
        teacher.setCreatedAt(now);
        teacher.setUpdatedAt(now);

        assertEquals(42L, teacher.getId());
        assertEquals("Alice", teacher.getFirstName());
        assertEquals("Smith", teacher.getLastName());
        assertEquals(now, teacher.getCreatedAt());
        assertEquals(now, teacher.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode_sameValues() {
        LocalDateTime now = LocalDateTime.now();
        Teacher t1 = Teacher.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .createdAt(now)
                .updatedAt(now)
                .build();

        Teacher t2 = Teacher.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .createdAt(now)
                .updatedAt(now)
                .build();

        assertEquals(t1, t2);
        assertEquals(t1.hashCode(), t2.hashCode());
    }

    @Test
    void testEqualsAndHashCode_differentValues() {
        LocalDateTime now = LocalDateTime.now();
        Teacher t1 = Teacher.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .createdAt(now)
                .updatedAt(now)
                .build();

        Teacher t2 = Teacher.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .createdAt(now)
                .updatedAt(now)
                .build();

        assertNotEquals(t1, t2);
        assertNotEquals(t1.hashCode(), t2.hashCode());
    }

    @Test
    void testEqualsWithNullAndDifferentType() {
        Teacher teacher = Teacher.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .build();

        assertNotEquals(teacher, null);
        assertNotEquals(teacher, "some string");
    }

    @Test
    void testHashSetContains() {
        Teacher t1 = Teacher.builder().id(1L).firstName("John").lastName("Doe").build();
        Teacher t2 = Teacher.builder().id(1L).firstName("John").lastName("Doe").build();

        Set<Teacher> set = new HashSet<>();
        set.add(t1);

        assertTrue(set.contains(t2));
    }

    @Test
    void testEquals_nullId() {
        Teacher t1 = Teacher.builder().id(null).build();
        Teacher t2 = Teacher.builder().id(null).build();

        assertEquals(t1, t2); // Depending on how equals is implemented
        assertEquals(t1.hashCode(), t2.hashCode());
    }

    @Test
    void testEquals_differentIdOnly() {
        Teacher t1 = Teacher.builder().id(1L).build();
        Teacher t2 = Teacher.builder().id(2L).build();

        assertNotEquals(t1, t2);
    }

    @Test
    void testBuilderCreatesValidObject() {
        LocalDateTime now = LocalDateTime.now();
        Teacher teacher = Teacher.builder()
                .id(123L)
                .firstName("Mike")
                .lastName("Tyson")
                .createdAt(now)
                .updatedAt(now)
                .build();

        assertEquals(123L, teacher.getId());
        assertEquals("Mike", teacher.getFirstName());
        assertEquals("Tyson", teacher.getLastName());
        assertEquals(now, teacher.getCreatedAt());
        assertEquals(now, teacher.getUpdatedAt());
    }

    @Test
    void testHashCodeConsistency() {
        Teacher t = Teacher.builder().id(77L).firstName("Zoe").lastName("Lee").build();
        int hash1 = t.hashCode();
        int hash2 = t.hashCode();
        assertEquals(hash1, hash2);
    }
}
