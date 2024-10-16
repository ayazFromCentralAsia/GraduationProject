package com.example.delivery.service.Dto.Address;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddressResponse {
    UUID id;
    String city;
    String street;
    String houseNumber;
    String apartment;
    String postalCode;
}
