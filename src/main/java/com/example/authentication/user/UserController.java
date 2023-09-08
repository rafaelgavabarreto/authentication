package com.example.authentication.user;

import com.example.authentication.auth.AuthenticationResponse;
import com.example.authentication.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("{id}")
    public ResponseEntity<Optional<UserDTO>> getUserById (@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers () {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PatchMapping("{id}")
    public ResponseEntity<Optional<User>> updateUserById (@PathVariable UUID id, @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(Optional.ofNullable(userService.updateById(id, request)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteUserById (@PathVariable UUID id) {
        return ResponseEntity.ok(userService.deleteById(id));
    }

}
