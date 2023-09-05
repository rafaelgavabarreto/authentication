package com.example.authentication.user;

public record UserDTO(UserRole userRole, String firstName, String lastName, String email, String password) {
}
