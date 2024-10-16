package com.example.delivery.service.Dto.Parcels;

import com.example.delivery.service.Entities.Address;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateParcelsRequest {
    @NotBlank(message = "{field.required}")
    private String pickupAddressId;

    @NotBlank(message = "{field.required}")
    private String deliveryAddressId;

    @NotBlank(message = "{field.required}")
    private double weight;
}

