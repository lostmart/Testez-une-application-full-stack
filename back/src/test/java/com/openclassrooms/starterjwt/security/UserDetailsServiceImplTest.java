package com.openclassrooms.starterjwt.security;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.security.services.UserDetailsServiceImpl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    public UserDetailsServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername_found() {
        User user = new User();
        user.setEmail("email@test.com");
        user.setPassword("secret");

        when(userRepository.findByEmail("email@test.com")).thenReturn(Optional.of(user));

        var details = userDetailsService.loadUserByUsername("email@test.com");

        assertThat(details).isNotNull();
        assertThat(details.getUsername()).isEqualTo("email@test.com");
    }

    @Test
    void testLoadUserByUsername_notFound() {
        when(userRepository.findByEmail("notfound@test.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("notfound@test.com");
        });
    }
}