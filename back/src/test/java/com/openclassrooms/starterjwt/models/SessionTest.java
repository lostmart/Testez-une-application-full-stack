package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class SessionTest {

    @Test
    void testSessionBuilder() {
        Teacher teacher = Teacher.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .build();

        User user = User.builder()
                .id(1L)
                .email("test@example.com")
                .firstName("Jane")
                .lastName("Smith")
                .password("password123")
                .build();

        Date date = new Date();
        LocalDateTime now = LocalDateTime.now();

        Session session = Session.builder()
                .id(1L)
                .name("test Yoga")
                .date(date)
                .description("a test yoga session")
                .teacher(teacher)
                .users(Collections.singletonList(user))
                .createdAt(now)
                .updatedAt(now)
                .build();

        assertEquals(1L, session.getId());
        assertEquals("test Yoga", session.getName());
        assertEquals(date, session.getDate());
        assertEquals("a test yoga session", session.getDescription());
        assertEquals(teacher, session.getTeacher());
        assertEquals(1, session.getUsers().size());
        assertEquals(user, session.getUsers().get(0));
        assertEquals(now, session.getCreatedAt());
        assertEquals(now, session.getUpdatedAt());
    }

    @Test
    void testSessionEqualityAndHashCode() {
        Session s1 = Session.builder().id(1L).name("Test").build();
        Session s2 = Session.builder().id(1L).name("Test").build();
        Session s3 = Session.builder().id(2L).name("Test").build();

        assertEquals(s1, s2);
        assertNotEquals(s1, s3);
        assertEquals(s1.hashCode(), s2.hashCode());
        assertNotEquals(s1.hashCode(), s3.hashCode());
    }

    @Test
    void testSetters() {
        Session session = new Session();
        Date date = new Date();
        LocalDateTime now = LocalDateTime.now();
        Teacher teacher = new Teacher();

        session.setId(10L);
        session.setName("Test Session");
        session.setDate(date);
        session.setDescription("Testing setters");
        session.setTeacher(teacher);
        session.setUsers(Collections.emptyList());
        session.setCreatedAt(now);
        session.setUpdatedAt(now);

        assertEquals(10L, session.getId());
        assertEquals("Test Session", session.getName());
        assertEquals(date, session.getDate());
        assertEquals("Testing setters", session.getDescription());
        assertEquals(teacher, session.getTeacher());
        assertEquals(0, session.getUsers().size());
        assertEquals(now, session.getCreatedAt());
        assertEquals(now, session.getUpdatedAt());
    }

    @Test
    void testEqualsWithNullAndOtherTypes() {
        Session session = Session.builder().id(1L).build();

        assertNotEquals(null, session);
        assertNotEquals("NotASession", session);
    }

    @Test
    void testToStringAndCanEqual() {
        Session session = Session.builder().id(5L).name("Stretching").build();
        String toString = session.toString();

        assertTrue(toString.contains("Stretching"));
        assertTrue(session.canEqual(Session.builder().id(5L).build()));
    }
}
