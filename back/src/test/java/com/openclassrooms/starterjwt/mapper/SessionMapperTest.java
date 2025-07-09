package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.TeacherService;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SessionMapperTest {

    private final TeacherService teacherService = mock(TeacherService.class);
    private final UserService userService = mock(UserService.class);

    private SessionMapper sessionMapper; 

    @BeforeEach
    void setUp() {
        sessionMapper = new SessionMapperImpl();
        sessionMapper.teacherService = teacherService;
        sessionMapper.userService = userService;
    }

    @Test
    void toEntity_withTeacherAndUsers_shouldMapCorrectly() {
        Long teacherId = 1L;
        List<Long> userIds = Arrays.asList(10L, 20L);

        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        when(teacherService.findById(teacherId)).thenReturn(teacher);

        User user1 = new User();
        user1.setId(10L);
        User user2 = new User();
        user2.setId(20L);

        when(userService.findById(10L)).thenReturn(user1);
        when(userService.findById(20L)).thenReturn(user2);

        SessionDto dto = SessionDto.builder()
                .id(5L)
                .description("Yoga class")
                .teacher_id(teacherId)
                .users(userIds)
                .date(Timestamp.valueOf(LocalDateTime.now()))
                .name("Morning Yoga")
                .build();

        Session entity = sessionMapper.toEntity(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getTeacher()).isEqualTo(teacher);
        assertThat(entity.getUsers()).containsExactly(user1, user2);
        assertThat(entity.getDescription()).isEqualTo(dto.getDescription());
    }

    @Test
    void toEntity_withNullUsers_shouldHandleGracefully() {
        Long teacherId = 1L;
        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        when(teacherService.findById(teacherId)).thenReturn(teacher);

        SessionDto dto = SessionDto.builder()
                .id(6L)
                .description("Empty class")
                .teacher_id(teacherId)
                .users(null)
                .date(Timestamp.valueOf(LocalDateTime.now()))
                .name("Solo Yoga")
                .build();

        Session entity = sessionMapper.toEntity(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getUsers()).isEmpty();
        assertThat(entity.getTeacher()).isEqualTo(teacher);
    }

    @Test
    void toEntity_withNullTeacher_shouldHandleGracefully() {
        SessionDto dto = SessionDto.builder()
                .id(6L)
                .description("Class without teacher")
                .teacher_id(null)
                .users(Collections.emptyList())
                .date(Timestamp.valueOf(LocalDateTime.now()))
                .name("Mystery Yoga")
                .build();

        Session entity = sessionMapper.toEntity(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getTeacher()).isNull();
        assertThat(entity.getUsers()).isEmpty();
    }

    @Test
    void toDto_shouldMapFieldsCorrectly() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);

        User user1 = new User();
        user1.setId(10L);
        User user2 = new User();
        user2.setId(20L);

        Session session = Session.builder()
                .id(7L)
                .description("Pilates")
                .teacher(teacher)
                .users(Arrays.asList(user1, user2))
                .build();

        SessionDto dto = sessionMapper.toDto(session);

        assertThat(dto).isNotNull();
        assertThat(dto.getTeacher_id()).isEqualTo(1L);
        assertThat(dto.getUsers()).containsExactly(10L, 20L);
        assertThat(dto.getDescription()).isEqualTo(session.getDescription());
    }

    @Test
    void toDto_withNullUsers_shouldReturnEmptyUserList() {
        Teacher teacher = new Teacher();
        teacher.setId(2L);

        Session session = Session.builder()
                .id(8L)
                .description("Meditation")
                .teacher(teacher)
                .users(null)
                .build();

        SessionDto dto = sessionMapper.toDto(session);

        assertThat(dto).isNotNull();
        assertThat(dto.getUsers()).isEmpty();
    }

    @Test
    void toEntityList_shouldMapListCorrectly() {
        SessionDto dto = SessionDto.builder()
                .id(1L)
                .teacher_id(1L)
                .users(Collections.emptyList())
                .description("Desc")
                .name("Test")
                .date(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        when(teacherService.findById(1L)).thenReturn(new Teacher());

        List<Session> result = sessionMapper.toEntity(List.of(dto));

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getDescription()).isEqualTo("Desc");
    }

    @Test
    void toDtoList_shouldMapListCorrectly() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);

        Session session = Session.builder()
                .id(1L)
                .teacher(teacher)
                .users(Collections.emptyList())
                .description("Desc")
                .name("Test")
                .date(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        List<SessionDto> result = sessionMapper.toDto(List.of(session));

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getDescription()).isEqualTo("Desc");
    }

    // @Test
    // void sessionTeacherId_withTeacher_shouldReturnId() {
    //     Teacher teacher = new Teacher();
    //     teacher.setId(9L);

    //     Session session = new Session();
    //     session.setTeacher(teacher);

    //     // Fix: sessionMapper should be declared as SessionMapper, not SessionMapperImpl
    //     Long result = sessionMapper.sessionTeacherId(session);

    //     assertThat(result).isEqualTo(9L);
    // }

    // @Test
    // void sessionTeacherId_withNullTeacher_shouldReturnNull() {
    //     Session session = new Session();
    //     session.setTeacher(null);

    //     Long result = sessionMapper.sessionTeacherId(session);

    //     assertThat(result).isNull();
    // }
}
