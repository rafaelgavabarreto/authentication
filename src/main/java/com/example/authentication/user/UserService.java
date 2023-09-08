package com.example.authentication.user;

import com.example.authentication.auth.RegisterRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private static final String USER_NOT_FOUND_MESSAGE = "User with email %s not found";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDTOMapper userDTOMapper;

    public Optional<UserDTO> getUserById(UUID id) {
        return this.userRepository.findById(id).map(userDTOMapper);
    }

    public List<UserDTO> getAllUsers() {
        return this.userRepository.findAll().stream().map(userDTOMapper).collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, email)));
    }

    public User updateById(UUID id, RegisterRequest request) {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findById(id).orElseThrow());
        User user = optionalUser.get();
        user.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .userRole(UserRole.USER)
                .build();
        return userRepository.save(user);
    }

    public Boolean deleteById(UUID id) {
        userRepository.deleteById(id);
        return true;
    }
}
