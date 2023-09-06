package com.example.authentication.user;

import java.util.UUID;

public record UserDTO (
        UUID id,
        String firstName,
        String lastName,
        String email,
        UserRole userRole
        ) {
}
