package com.openclassrooms.starterjwt.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;

    @NonNull
    @Size(max = 50)
    @Email
    private String email;

    @NonNull
    @Size(max = 20)
    private String lastName;

    @NonNull
    @Size(max = 20)
    private String firstName;

    private boolean admin;

    @JsonIgnore
    @Size(max = 120)
    private String password;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public UserDto(Long id, String email, String lastName, String firstName, boolean admin, String password) {
        this.id = id;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.admin = admin;
        this.password = password;
        this.createdAt = null;
        this.updatedAt = null;
    }
}
