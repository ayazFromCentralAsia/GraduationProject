package com.example.delivery.service.Dto.Couriers;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CouriersResponse {
    private UUID id;
    private String username;
    private String email;
    private String phone;
}