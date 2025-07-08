package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SessionControllerTest {

    private SessionService sessionService;
    private SessionMapper sessionMapper;
    private SessionController sessionController;

    @BeforeEach
    void setUp() {
        sessionService = mock(SessionService.class);
        sessionMapper = mock(SessionMapper.class);
        sessionController = new SessionController(sessionService, sessionMapper);
    }

    @Test
    void findById_validId_returnsSession() {
        Session session = new Session();
        session.setId(1L);
        SessionDto sessionDto = new SessionDto();
        sessionDto.setId(1L);

        when(sessionService.getById(1L)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        ResponseEntity<?> response = sessionController.findById("1");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(sessionDto, response.getBody());
    }

    @Test
    void findById_invalidId_returns400() {
        ResponseEntity<?> response = sessionController.findById("abc");
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void findById_notFound_returns404() {
        when(sessionService.getById(99L)).thenReturn(null);
        ResponseEntity<?> response = sessionController.findById("99");
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void findAll_returnsSessionsList() {
        List<Session> sessions = Arrays.asList(new Session(), new Session());
        List<SessionDto> sessionDtos = Arrays.asList(new SessionDto(), new SessionDto());

        when(sessionService.findAll()).thenReturn(sessions);
        when(sessionMapper.toDto(sessions)).thenReturn(sessionDtos);

        ResponseEntity<?> response = sessionController.findAll();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(sessionDtos, response.getBody());
    }

    @Test
    void create_validSession_returnsCreatedSession() {
        SessionDto sessionDto = new SessionDto();
        Session session = new Session();

        when(sessionMapper.toEntity(sessionDto)).thenReturn(session);
        when(sessionService.create(session)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        ResponseEntity<?> response = sessionController.create(sessionDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(sessionDto, response.getBody());
    }

    @Test
    void update_validId_returnsUpdatedSession() {
        SessionDto sessionDto = new SessionDto();
        Session session = new Session();

        when(sessionMapper.toEntity(sessionDto)).thenReturn(session);
        when(sessionService.update(1L, session)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        ResponseEntity<?> response = sessionController.update("1", sessionDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(sessionDto, response.getBody());
    }

    @Test
    void update_invalidId_returns400() {
        ResponseEntity<?> response = sessionController.update("invalid", new SessionDto());
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void delete_validId_returns200() {
        Session session = new Session();
        when(sessionService.getById(1L)).thenReturn(session);
        doNothing().when(sessionService).delete(1L);

        ResponseEntity<?> response = sessionController.save("1");

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void delete_invalidId_returns400() {
        ResponseEntity<?> response = sessionController.save("invalid");
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void delete_nonExisting_returns404() {
        when(sessionService.getById(99L)).thenReturn(null);
        ResponseEntity<?> response = sessionController.save("99");

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void participate_validIds_returns200() {
        doNothing().when(sessionService).participate(1L, 2L);
        ResponseEntity<?> response = sessionController.participate("1", "2");
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void participate_invalidIds_returns400() {
        ResponseEntity<?> response = sessionController.participate("abc", "2");
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void noLongerParticipate_validIds_returns200() {
        doNothing().when(sessionService).noLongerParticipate(1L, 2L);
        ResponseEntity<?> response = sessionController.noLongerParticipate("1", "2");
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void noLongerParticipate_invalidIds_returns400() {
        ResponseEntity<?> response = sessionController.noLongerParticipate("a", "b");
        assertEquals(400, response.getStatusCodeValue());
    }
}
