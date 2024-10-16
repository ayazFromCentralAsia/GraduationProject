package com.example.delivery.service.Enum;


import lombok.Getter;

@Getter
public enum Status {
    PENDING,
    IN_TRANSIT,
    DELIVERED,
    CANCELED
}
