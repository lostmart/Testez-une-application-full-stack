package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TeacherControllerTest {

    private TeacherService teacherService;
    private TeacherMapper teacherMapper;
    private TeacherController teacherController;

    @BeforeEach
    public void setup() {
        teacherService = mock(TeacherService.class);
        teacherMapper = mock(TeacherMapper.class);
        teacherController = new TeacherController(teacherService, teacherMapper);
    }

    @Test
    public void testFindById_withValidId_returnsTeacher() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(1L);

        when(teacherService.findById(1L)).thenReturn(teacher);
        when(teacherMapper.toDto(teacher)).thenReturn(teacherDto);

        ResponseEntity<?> response = teacherController.findById("1");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(teacherDto, response.getBody());
    }

    @Test
    public void testFindById_withNonExistentId_returns404() {
        when(teacherService.findById(99L)).thenReturn(null);

        ResponseEntity<?> response = teacherController.findById("99");

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testFindById_withInvalidId_returns400() {
        ResponseEntity<?> response = teacherController.findById("abc");

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void testFindAll_returnsListOfTeachers() {
        List<Teacher> teachers = Arrays.asList(new Teacher(), new Teacher());
        List<TeacherDto> teacherDtos = Arrays.asList(new TeacherDto(), new TeacherDto());

        when(teacherService.findAll()).thenReturn(teachers);
        when(teacherMapper.toDto(teachers)).thenReturn(teacherDtos);

        ResponseEntity<?> response = teacherController.findAll();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(teacherDtos, response.getBody());
    }
}
