package com.example.delivery.service.Dto.User;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateProfileRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String phone;
}
