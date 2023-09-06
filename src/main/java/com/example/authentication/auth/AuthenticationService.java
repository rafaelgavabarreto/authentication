package com.example.authentication.auth;

import com.example.authentication.auth.token.ConfirmationToken;
import com.example.authentication.auth.token.ConfirmationTokenService;
import com.example.authentication.config.JwtService;
import com.example.authentication.user.User;
import com.example.authentication.user.UserRepository;
import com.example.authentication.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ConfirmationTokenService confirmationTokenService;

    public AuthenticationResponse register(RegisterRequest request) {
        //        boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();
        //        if (userExists) {
        //            throw new IllegalStateException("Email already taken");
        //        }
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .userRole(UserRole.USER)
                .build();
        userRepository.save(user);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                user,
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15)
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        // TODO: Send an email to the user

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .accessToken(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        System.out.println("authenticate " + request.getEmail() + " " + request.getPassword());
        var user2 = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        System.out.println("user1" + user2);
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getEmail(),
//                        request.getPassword()
//                )
//        );
        System.out.println("authenticationManager working");

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        System.out.println("user" + user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .accessToken(jwtToken)
                .build();
    }
}
