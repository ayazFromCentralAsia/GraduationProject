package com.example.delivery.service.Api;

import com.example.delivery.service.Dto.User.UserSignUpRequest;
import com.example.delivery.service.Dto.User.UserSingInRequest;
import com.example.delivery.service.Dto.User.UserSingInResponse;
import com.example.delivery.service.Service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication API")
public class AuthApi {
    private final AuthenticationService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<?> register(@RequestBody @Valid UserSignUpRequest user) {
        try {
            authService.createUser(user);
            return ResponseEntity.status(HttpStatus.OK).body("User created successfully");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating user");
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Login a user")
    public ResponseEntity<?> login(@RequestBody @Valid UserSingInRequest user) {
        try {
            UserSingInResponse token = authService.login(user);
            return ResponseEntity.status(HttpStatus.OK).body("token: " + token.getToken()+"\n"+"role: " + token.getRole());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error logging in user");
        }
    }
}
