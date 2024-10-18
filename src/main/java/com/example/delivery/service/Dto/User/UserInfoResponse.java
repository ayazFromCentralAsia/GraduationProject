package com.example.delivery.service.Dto.User;


import com.example.delivery.service.Enum.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class UserInfoResponse {
    UUID id;
    String username;
    String email;
    Role role;
}
