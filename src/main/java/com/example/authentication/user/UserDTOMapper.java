package com.example.authentication.user;

import java.util.function.Function;
import java.util.stream.Collectors;

public class UserDTOMapper implements Function<User, UserDTO> {

    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getUserRole()
        );
    }

}
