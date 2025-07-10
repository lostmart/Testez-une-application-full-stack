package com.openclassrooms.starterjwt.payload.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;


import static org.assertj.core.api.Assertions.assertThat;

class SignupRequestTest {

    private final Validator validator;

    public SignupRequestTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // ==================== Validation Tests ====================
    @Test
    void validSignupRequest_shouldHaveNoViolations() {
        SignupRequest request = new SignupRequest();
        request.setEmail("test@example.com");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setPassword("password123");

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(request);
        assertThat(violations).isEmpty();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " ", "  " })
    void email_blank_shouldHaveViolation(String email) {
        SignupRequest request = createValidRequest();
        request.setEmail(email);

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(request);
        assertThat(violations)
                .hasSizeGreaterThanOrEqualTo(1)
                .extracting("message")
                .contains("must not be blank");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "invalid-email", "test@", "@example.com", "test@.com", "test@example..com"
    })
    void email_invalidFormat_shouldHaveViolation(String email) {
        SignupRequest request = createValidRequest();
        request.setEmail(email);

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(request);
        assertThat(violations)
                .hasSize(1)
                .extracting("message")
                .contains("must be a well-formed email address");
    }

    @Test
    void email_tooLong_shouldHaveViolation() {
        SignupRequest request = createValidRequest();
        request.setEmail("a".repeat(51) + "@example.com");

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(request);
        assertThat(violations)
                .hasSize(1)
                .extracting("message")
                .contains("size must be between 0 and 50");
    }

    // ... [other validation tests for firstName, lastName, password] ...

    // ==================== Lombok Generated Methods Tests ====================
    @Test
    void testEqualsAndHashCode() {
        SignupRequest request1 = new SignupRequest();
        request1.setEmail("test@example.com");
        request1.setFirstName("John");
        request1.setLastName("Doe");
        request1.setPassword("password123");

        SignupRequest request2 = new SignupRequest();
        request2.setEmail("test@example.com");
        request2.setFirstName("John");
        request2.setLastName("Doe");
        request2.setPassword("password123");

        // Test equals
        assertThat(request1).isEqualTo(request2);
        assertThat(request1).isEqualTo(request1);
        assertThat(request1).isNotEqualTo(null);
        assertThat(request1).isNotEqualTo(new Object());

        // Test equals with different values
        SignupRequest differentRequest = new SignupRequest();
        differentRequest.setEmail("different@example.com");
        assertThat(request1).isNotEqualTo(differentRequest);

        // Test hashCode
        assertThat(request1.hashCode()).isEqualTo(request2.hashCode());
    }

    @Test
    void testToString() {
        SignupRequest request = new SignupRequest();
        request.setEmail("test@example.com");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setPassword("password123");

        String toStringResult = request.toString();
        assertThat(toStringResult)
                .contains("test@example.com")
                .contains("John")
                .contains("Doe")
                .contains("password123"); // Accept that password is included
    }

    @Test
    void testGettersAndSetters() {
        SignupRequest request = new SignupRequest();

        // Test setters and getters
        request.setEmail("test@example.com");
        assertThat(request.getEmail()).isEqualTo("test@example.com");

        request.setFirstName("John");
        assertThat(request.getFirstName()).isEqualTo("John");

        request.setLastName("Doe");
        assertThat(request.getLastName()).isEqualTo("Doe");

        request.setPassword("password123");
        assertThat(request.getPassword()).isEqualTo("password123");
    }

    @Test
    void testCanEqual() {
        SignupRequest request1 = new SignupRequest();
        SignupRequest request2 = new SignupRequest();

        // This tests the canEqual method generated by Lombok
        assertThat(request1).isEqualTo(request2);
    }

    // ==================== Helper Methods ====================
        // private static Stream<String> validPasswordLengthProvider() {
        //     return Stream.of("a", "ab", "a".repeat(21));
        // }

        // private static Stream<String> invalidPasswordLengthProvider() {
        //     return Stream.of("a", "abcde", "a".repeat(41));
        // }

    private SignupRequest createValidRequest() {
        SignupRequest request = new SignupRequest();
        request.setEmail("valid@example.com");
        request.setFirstName("Valid");
        request.setLastName("User");
        request.setPassword("validPassword123");
        return request;
    }
}