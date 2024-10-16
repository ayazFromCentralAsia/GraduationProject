package com.example.delivery.service.Dto.User;

import com.example.delivery.service.Dto.Address.AddressResponse;
import com.example.delivery.service.Enum.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ProfileInfoResponse {
    private UUID id;
    private String username;
    private String email;
    private String phone;
    private Role role;
    private List<AddressResponse> addressList;
}
