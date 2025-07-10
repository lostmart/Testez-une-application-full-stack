package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import com.openclassrooms.starterjwt.payload.response.MessageResponse;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private JwtUtils jwtUtils;
    private UserRepository userRepository;
    private AuthController authController;

    @BeforeEach
    void setUp() {
        authenticationManager = mock(AuthenticationManager.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtUtils = mock(JwtUtils.class);
        userRepository = mock(UserRepository.class);
        authController = new AuthController(authenticationManager, passwordEncoder, jwtUtils, userRepository);
    }

    @Test
    void authenticateUser_shouldReturnJwtResponse() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        Authentication authentication = mock(Authentication.class);
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);

        when(userDetails.getId()).thenReturn(1L);
        when(userDetails.getUsername()).thenReturn("test@example.com");
        when(userDetails.getFirstName()).thenReturn("First");
        when(userDetails.getLastName()).thenReturn("Last");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn("mockJwt");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(new User()));

        ResponseEntity<?> response = authController.authenticateUser(loginRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof JwtResponse);

        Optional<JwtResponse> jwtResponseOptional = Optional.ofNullable((JwtResponse) response.getBody());
        jwtResponseOptional.ifPresent(jwtResponse -> {
            assertEquals("mockJwt", jwtResponse.getToken());
            assertEquals("test@example.com", jwtResponse.getUsername());
        });
    }

    @Test
    void registerUser_shouldReturnErrorWhenEmailExists() {
        SignupRequest request = new SignupRequest();
        request.setEmail("existing@example.com");

        when(userRepository.existsByEmail("existing@example.com")).thenReturn(true);

        ResponseEntity<?> response = authController.registerUser(request);

        assertEquals(400, response.getStatusCodeValue());
        Object responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody instanceof MessageResponse);
        assertEquals("Error: Email is already taken!", ((MessageResponse) responseBody).getMessage());
    }

    @Test
    void registerUser_shouldRegisterNewUser() {
        SignupRequest request = new SignupRequest();
        request.setEmail("new@example.com");
        request.setFirstName("First");
        request.setLastName("Last");
        request.setPassword("password");

        when(userRepository.existsByEmail("new@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encoded-password");

        ResponseEntity<?> response = authController.registerUser(request);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof MessageResponse);
        Object responseBody = response.getBody();
        if (responseBody != null) {
            assertTrue(responseBody instanceof MessageResponse);
            assertEquals("User registered successfully!", ((MessageResponse) responseBody).getMessage());
        } else {
            // Handle the case where responseBody is null
            fail("Response body is null");
        }

        verify(userRepository, times(1)).save(any(User.class));
    }
}
