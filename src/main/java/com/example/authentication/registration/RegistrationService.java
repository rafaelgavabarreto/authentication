//package com.example.authentication.registration;
//
//import com.example.authentication.user.User;
//import com.example.authentication.user.UserRole;
//import com.example.authentication.user.UserService;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@AllArgsConstructor
//public class RegistrationService {
//
//    private final EmailValidator emailValidator;
//    private final UserService userService;
//
//    public String register(RegistrationRequest request) {
//        boolean isValidEmail = emailValidator.test(request.getEmail());
//        if(!isValidEmail) {
//            throw new IllegalStateException("Email not valid");
//        }
//        return userService.createUser(new User(
//                request.getFirstName(),
//                request.getLastName(),
//                request.getEmail(),
//                request.getPassword(),
//                UserRole.USER,
//                true,
//                false
//        ));
//    }
//}
