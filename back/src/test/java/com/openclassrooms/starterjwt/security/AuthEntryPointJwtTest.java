package com.openclassrooms.starterjwt.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.security.jwt.AuthEntryPointJwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthEntryPointJwtTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private AuthenticationException authException;

    @InjectMocks
    private AuthEntryPointJwt authEntryPointJwt;

    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() throws IOException {
        outputStream = new ByteArrayOutputStream();
        when(response.getOutputStream()).thenReturn(new ServletOutputStreamStub(outputStream));
        when(request.getServletPath()).thenReturn("/api/test");
        when(authException.getMessage()).thenReturn("Test error message");
    }

    @Test
    void shouldHandleUnauthorizedRequest() throws Exception {
        // When
        authEntryPointJwt.commence(request, response, authException);

        // Then verify response setup
        verify(response).setContentType(MediaType.APPLICATION_JSON_VALUE);
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Verify response body
        String responseBody = outputStream.toString();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> responseMap = mapper.readValue(responseBody, HashMap.class);

        assertThat(responseMap)
            .containsEntry("status", HttpServletResponse.SC_UNAUTHORIZED)
            .containsEntry("error", "Unauthorized")
            .containsEntry("message", "Test error message")
            .containsEntry("path", "/api/test");
    }

    // @Test
    // void shouldHandleIOExceptionWhenWritingResponse() throws Exception {
    //     // Setup to throw IOException when getting output stream
    //     when(response.getOutputStream()).thenThrow(new IOException("Test IO Exception"));

    //     // When
    //     authEntryPointJwt.commence(request, response, authException);

    //     // Then verify response was still attempted to be set up
    //     verify(response).setContentType(MediaType.APPLICATION_JSON_VALUE);
    //     verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    // }

    // Helper class to stub ServletOutputStream
    private static class ServletOutputStreamStub extends javax.servlet.ServletOutputStream {
        private final ByteArrayOutputStream outputStream;

        public ServletOutputStreamStub(ByteArrayOutputStream outputStream) {
            this.outputStream = outputStream;
        }

        @Override
        public void write(int b) throws IOException {
            outputStream.write(b);
        }

        @Override
        public void write(byte[] b) throws IOException {
            outputStream.write(b);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            outputStream.write(b, off, len);
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setWriteListener(javax.servlet.WriteListener writeListener) {
            // Not implemented for testing
        }
    }
}