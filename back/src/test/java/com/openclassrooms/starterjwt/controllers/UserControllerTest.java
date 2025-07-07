package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.UserService;
import com.openclassrooms.starterjwt.dto.UserDto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testFindById_ShouldReturnUser_WhenUserExists() {
        // Arrange
        Long userId = 1L;
        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setEmail("user@example.com");

        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setEmail("user@example.com");

        when(userService.findById(userId)).thenReturn(mockUser);
        when(userMapper.toDto(mockUser)).thenReturn(userDto);

        // Act
        ResponseEntity<?> response = userController.findById(userId.toString());

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userDto, response.getBody());
    }

    @Test
    void testFindById_ShouldReturnNotFound_WhenUserDoesNotExist() {
        // Arrange
        Long userId = 2L;
        when(userService.findById(userId)).thenReturn(null);

        // Act
        ResponseEntity<?> response = userController.findById(userId.toString());

        // Assert
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testFindById_ShouldReturnBadRequest_WhenIdIsInvalid() {
        // Act
        ResponseEntity<?> response = userController.findById("invalid");

        // Assert
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testDeleteUser_ShouldDeleteUser_WhenAuthorized() {
        // Arrange
        Long userId = 1L;
        String email = "user@example.com";

        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setEmail(email);

        // Mock UserDetails returned from SecurityContext
        UserDetails mockUserDetails = mock(UserDetails.class);
        when(mockUserDetails.getUsername()).thenReturn(email);

        // Mock security context
        Authentication mockAuthentication = mock(Authentication.class);
        when(mockAuthentication.getPrincipal()).thenReturn(mockUserDetails);

        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);

        SecurityContextHolder.setContext(mockSecurityContext);

        when(userService.findById(userId)).thenReturn(mockUser);

        // Act
        ResponseEntity<?> response = userController.save(userId.toString());

        // Assert
        verify(userService).delete(userId);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testDeleteUser_ShouldReturnUnauthorized_WhenUserEmailMismatch() {
        // Arrange
        Long userId = 1L;
        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setEmail("user@example.com");

        UserDetails mockUserDetails = mock(UserDetails.class);
        when(mockUserDetails.getUsername()).thenReturn("other@example.com"); // mismatch

        Authentication mockAuthentication = mock(Authentication.class);
        when(mockAuthentication.getPrincipal()).thenReturn(mockUserDetails);

        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);

        SecurityContextHolder.setContext(mockSecurityContext);

        when(userService.findById(userId)).thenReturn(mockUser);

        // Act
        ResponseEntity<?> response = userController.save(userId.toString());

        // Assert
        verify(userService, never()).delete(anyLong());
        assertEquals(401, response.getStatusCodeValue());
    }

}
