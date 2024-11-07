package com.example.delivery.service.Dto.Address;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AddressRequest {

    @NotBlank(message = "{field.required}")
    String city;
    @NotBlank(message = "{field.required}")
    String street;
    @NotBlank(message = "{field.required}")
    String houseNumber;

    String apartment;
    @NotBlank(message = "{field.required}")
    String postalCode;
}
