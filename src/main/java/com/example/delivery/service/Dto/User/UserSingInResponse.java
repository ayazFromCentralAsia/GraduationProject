package com.example.delivery.service.Dto.User;

import com.example.delivery.service.Enum.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSingInResponse {
    private String token;

    private Role role;
}
