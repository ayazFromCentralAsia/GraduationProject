package com.example.delivery.service.Dto.User;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserSingInRequest {
    @NotBlank
    String username;
    @NotBlank
    String password;
}
