
package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void testToDto() {
        LocalDateTime now = LocalDateTime.now();
        User user = User.builder()
                .id(1L)
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .password("securePass")
                .admin(true)
                .createdAt(now)
                .updatedAt(now)
                .build();

        UserDto dto = userMapper.toDto(user); // ✅ ACTUAL mapping tested

        assertNotNull(dto);
        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(user.getFirstName(), dto.getFirstName());
        assertEquals(user.getLastName(), dto.getLastName());
        assertEquals(user.isAdmin(), dto.isAdmin());
        assertEquals(user.getCreatedAt(), dto.getCreatedAt());
        assertEquals(user.getUpdatedAt(), dto.getUpdatedAt());
    }

    @Test
    void testToEntity() {
        LocalDateTime now = LocalDateTime.now();
        UserDto dto = UserDto.builder()
                .id(2L)
                .email("jane@example.com")
                .firstName("Jane")
                .lastName("Smith")
                .admin(false)
                .password("test123") 
                .createdAt(now)
                .updatedAt(now)
                .build();

        User user = userMapper.toEntity(dto);

        assertNotNull(user);
        assertEquals(dto.getId(), user.getId());
        assertEquals(dto.getEmail(), user.getEmail());
        assertEquals(dto.getFirstName(), user.getFirstName());
        assertEquals(dto.getLastName(), user.getLastName());
        assertEquals(dto.isAdmin(), user.isAdmin());
        assertEquals(dto.getCreatedAt(), user.getCreatedAt());
        assertEquals(dto.getUpdatedAt(), user.getUpdatedAt());
    }

    // @Test
    // void testNullMapping() {
    // assertNull(userMapper.toDto(null));
    // assertNull(userMapper.toEntity(null));
    // }
}
