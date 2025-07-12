package com.openclassrooms.starterjwt.models;

import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import javax.persistence.EntityManager;

@DataJpaTest
@ActiveProfiles("test")
@Transactional
public class SessionRepositoryIntegrationTest {

    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        // Manually create schema if SQL file fails
        entityManager.createNativeQuery(
                "CREATE TABLE IF NOT EXISTS teachers (" +
                        "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                        "first_name VARCHAR(20) NOT NULL, " +
                        "last_name VARCHAR(20) NOT NULL, " +
                        "created_at TIMESTAMP NOT NULL, " +
                        "updated_at TIMESTAMP NOT NULL)")
                .executeUpdate();

        entityManager.createNativeQuery(
                "CREATE TABLE IF NOT EXISTS users (" +
                        "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                        "email VARCHAR(50) NOT NULL UNIQUE, " +
                        "first_name VARCHAR(20) NOT NULL, " +
                        "last_name VARCHAR(20) NOT NULL, " +
                        "password VARCHAR(120) NOT NULL, " +
                        "admin BOOLEAN NOT NULL, " +
                        "created_at TIMESTAMP NOT NULL, " +
                        "updated_at TIMESTAMP NOT NULL)")
                .executeUpdate();

        entityManager.createNativeQuery(
                "CREATE TABLE IF NOT EXISTS sessions (" +
                        "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                        "name VARCHAR(50) NOT NULL, " +
                        "date TIMESTAMP NOT NULL, " +
                        "description VARCHAR(2500) NOT NULL, " +
                        "teacher_id BIGINT NOT NULL, " +
                        "created_at TIMESTAMP NOT NULL, " +
                        "updated_at TIMESTAMP NOT NULL, " +
                        "FOREIGN KEY (teacher_id) REFERENCES teachers(id))")
                .executeUpdate();

        Teacher testTeacher = null;
        User testUser = null;

        testTeacher = teacherRepository.save(
                new Teacher(null, "Master", "Yoga", LocalDateTime.now(), LocalDateTime.now()));
        testUser = userRepository.save(
                new User(null, "student@yoga.com", "Doe", "John",
                        "password123", false, LocalDateTime.now(), LocalDateTime.now()));
    }

    @Test
    void should_store_and_retrieve_session() {
        Teacher teacher = teacherRepository.findAll().get(0); // Assuming 1 test teacher
        Session session = new Session();
        session.setName("Morning Yoga");
        session.setDate(Date.from(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant()));
        session.setDescription("A calm yoga session.");
        session.setTeacher(teacher);
        session.setCreatedAt(LocalDateTime.now());
        session.setUpdatedAt(LocalDateTime.now());

        Session savedSession = sessionRepository.save(session);
        Optional<Session> foundSession = sessionRepository.findById(savedSession.getId());

        assertThat(foundSession).isPresent();
        assertThat(foundSession.get().getName()).isEqualTo("Morning Yoga");
    }

}