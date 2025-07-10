package com.openclassrooms.starterjwt.payload.response;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class JwtResponseTest {

    @Test
    void constructor_shouldSetAllFieldsCorrectly() {
        // Given
        String token = "testToken";
        Long id = 1L;
        String username = "testUser";
        String firstName = "John";
        String lastName = "Doe";
        Boolean admin = true;

        // When
        JwtResponse response = new JwtResponse(token, id, username, firstName, lastName, admin);

        // Then
        assertThat(response.getToken()).isEqualTo(token);
        assertThat(response.getType()).isEqualTo("Bearer");
        assertThat(response.getId()).isEqualTo(id);
        assertThat(response.getUsername()).isEqualTo(username);
        assertThat(response.getFirstName()).isEqualTo(firstName);
        assertThat(response.getLastName()).isEqualTo(lastName);
        assertThat(response.getAdmin()).isEqualTo(admin);
    }

    @Test
    void setters_shouldUpdateFieldsCorrectly() {
        // Given
        JwtResponse response = new JwtResponse("token", 1L, "user", "John", "Doe", false);

        // When
        response.setToken("newToken");
        response.setType("newType");
        response.setId(2L);
        response.setUsername("newUser");
        response.setFirstName("Jane");
        response.setLastName("Smith");
        response.setAdmin(true);

        // Then
        assertThat(response.getToken()).isEqualTo("newToken");
        assertThat(response.getType()).isEqualTo("newType");
        assertThat(response.getId()).isEqualTo(2L);
        assertThat(response.getUsername()).isEqualTo("newUser");
        assertThat(response.getFirstName()).isEqualTo("Jane");
        assertThat(response.getLastName()).isEqualTo("Smith");
        assertThat(response.getAdmin()).isTrue();
    }

    @Test
    void equalsAndHashCode_shouldWorkCorrectly() {
        // Given
        JwtResponse response1 = createJwtResponse("token", 1L, "user", "John", "Doe", false);
        JwtResponse response2 = createJwtResponse("token", 1L, "user", "John", "Doe", false);
        JwtResponse differentResponse = createJwtResponse("different", 2L, "user2", "Jane", "Smith", true);

        // Then - Default equals() compares references
        assertThat(response1)
                .isEqualTo(response1) // Same instance
                .isNotEqualTo(differentResponse)
                .isNotEqualTo(null)
                .isNotEqualTo(new Object());

        // For field-based equality, we need to add @EqualsAndHashCode to the class
        assertThat(response2).isNotEqualTo(differentResponse);
    }

    @Test
    void toString_shouldContainClassName() {
        // Given
        JwtResponse response = new JwtResponse("token", 1L, "user", "John", "Doe", false);

        // When
        String toStringResult = response.toString();

        // Then - Default toString() just shows class name + hashcode
        assertThat(toStringResult)
                .startsWith("com.openclassrooms.starterjwt.payload.response.JwtResponse@");
    }

    private JwtResponse createJwtResponse(String token, Long id, String username,
            String firstName, String lastName, Boolean admin) {
        JwtResponse response = new JwtResponse(token, id, username, firstName, lastName, admin);
        return response;
    }
}