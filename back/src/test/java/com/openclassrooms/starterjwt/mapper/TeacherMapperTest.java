package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TeacherMapperTest {

    private final TeacherMapper teacherMapper = Mappers.getMapper(TeacherMapper.class);

    @Test
    void shouldMapTeacherDtoToEntity() {
        // Given
        TeacherDto dto = new TeacherDto();
        dto.setId(1L);
        dto.setLastName("Doe");
        dto.setFirstName("John");
        dto.setCreatedAt(LocalDateTime.now());
        dto.setUpdatedAt(LocalDateTime.now());

        // When
        Teacher entity = teacherMapper.toEntity(dto);

        // Then
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(dto.getId());
        assertThat(entity.getLastName()).isEqualTo(dto.getLastName());
        assertThat(entity.getFirstName()).isEqualTo(dto.getFirstName());
        assertThat(entity.getCreatedAt()).isEqualTo(dto.getCreatedAt());
        assertThat(entity.getUpdatedAt()).isEqualTo(dto.getUpdatedAt());
    }

    @Test
    void shouldMapTeacherToDto() {
        // Given
        Teacher teacher = new Teacher();
        teacher.setId(2L);
        teacher.setLastName("Smith");
        teacher.setFirstName("Jane");
        teacher.setCreatedAt(LocalDateTime.now());
        teacher.setUpdatedAt(LocalDateTime.now());

        // When
        TeacherDto dto = teacherMapper.toDto(teacher);

        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(teacher.getId());
        assertThat(dto.getLastName()).isEqualTo(teacher.getLastName());
        assertThat(dto.getFirstName()).isEqualTo(teacher.getFirstName());
        assertThat(dto.getCreatedAt()).isEqualTo(teacher.getCreatedAt());
        assertThat(dto.getUpdatedAt()).isEqualTo(teacher.getUpdatedAt());
    }

    @Test
    void shouldMapTeacherDtoListToEntityList() {
        // Given
        TeacherDto dto1 = new TeacherDto();
        dto1.setId(1L);
        dto1.setLastName("Doe");

        TeacherDto dto2 = new TeacherDto();
        dto2.setId(2L);
        dto2.setLastName("Smith");

        List<TeacherDto> dtoList = Arrays.asList(dto1, dto2);

        // When
        List<Teacher> entityList = teacherMapper.toEntity(dtoList);

        // Then
        assertThat(entityList).hasSize(2);
        assertThat(entityList.get(0).getId()).isEqualTo(dto1.getId());
        assertThat(entityList.get(0).getLastName()).isEqualTo(dto1.getLastName());
        assertThat(entityList.get(1).getId()).isEqualTo(dto2.getId());
        assertThat(entityList.get(1).getLastName()).isEqualTo(dto2.getLastName());
    }

    @Test
    void shouldMapTeacherListToDtoList() {
        // Given
        Teacher teacher1 = new Teacher();
        teacher1.setId(1L);
        teacher1.setLastName("Doe");

        Teacher teacher2 = new Teacher();
        teacher2.setId(2L);
        teacher2.setLastName("Smith");

        List<Teacher> teacherList = Arrays.asList(teacher1, teacher2);

        // When
        List<TeacherDto> dtoList = teacherMapper.toDto(teacherList);

        // Then
        assertThat(dtoList).hasSize(2);
        assertThat(dtoList.get(0).getId()).isEqualTo(teacher1.getId());
        assertThat(dtoList.get(0).getLastName()).isEqualTo(teacher1.getLastName());
        assertThat(dtoList.get(1).getId()).isEqualTo(teacher2.getId());
        assertThat(dtoList.get(1).getLastName()).isEqualTo(teacher2.getLastName());
    }

    @Test
    void shouldHandleNullInputs() {
        // Test null single entity
        assertThat(teacherMapper.toEntity((TeacherDto) null)).isNull();
        assertThat(teacherMapper.toDto((Teacher) null)).isNull();

        // Test null lists
        assertThat(teacherMapper.toEntity((List<TeacherDto>) null)).isNull();
        assertThat(teacherMapper.toDto((List<Teacher>) null)).isNull();
    }
}