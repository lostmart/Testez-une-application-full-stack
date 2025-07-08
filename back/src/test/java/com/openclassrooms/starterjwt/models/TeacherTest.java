package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TeacherTest {

    @Test
    void testSetters() {
        Teacher teacher = new Teacher();
        teacher.setLastName("Doe");
        teacher.setFirstName("John");
        teacher.setCreatedAt(LocalDateTime.now());

        assertEquals("Doe", teacher.getLastName());
        assertEquals("John", teacher.getFirstName());
        assertNotNull(teacher.getCreatedAt());
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
}
