package com.example.delivery.service.Dto.User;


import com.example.delivery.service.Validation.ValidPhoneNumber;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserSignUpRequest {
    @NotBlank(message = "{field.required}")
    String username;
    @NotBlank(message = "{field.required}")
    String email;
    @NotBlank(message = "{field.required}")
    String password;
    @ValidPhoneNumber
    String phone;
}
